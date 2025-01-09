import scrapy
from pymongo import MongoClient
from datetime import datetime

# MongoDB connection
client = MongoClient("mongodb+srv://lil_boo:lil_boo22@cluster0.qoqzo.mongodb.net/", serverSelectionTimeoutMS=5000)
db = client["linkedin_profiles"]

class LinkedInPeopleProfileSpider(scrapy.Spider):
    name = "linkedin_people_profile"

    # Remove FEEDS setting since we are not saving to JSON
    # custom_settings = {
    #     'FEEDS': { 'data/%(name)s_%(time)s.jsonl': { 'format': 'jsonlines',}}
    #     }

    def start_requests(self):
        profile_list = ['reidhoffman', 'williamhgates']
        headers = {
            'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36',
            'Accept-Language': 'en-US,en;q=0.9',
        }
        for profile in profile_list:
            linkedin_people_url = f'https://www.linkedin.com/in/{profile}/'
            yield scrapy.Request(url=linkedin_people_url, callback=self.parse_profile, headers=headers, meta={'profile': profile, 'linkedin_url': linkedin_people_url})

    def parse_profile(self, response):
        # Collecting the scraped data
        item = {
            'profile': response.meta['profile'],
            'url': response.meta['linkedin_url'],
            'name': response.css("section.top-card-layout h1::text").get().strip(),
            'description': response.css("section.top-card-layout h2::text").get().strip(),
            'location': '',
            'followers': '',
            'connections': '',
            'about': response.css('section.summary div.core-section-container__content p::text').get(),
            'experience': [],
            'education': [],
            'scraped_at': datetime.utcnow().isoformat(),  # Convert datetime to ISO 8601 string
        }

        # Location
        try:
            location = response.css('div.aMupHxipPGPIbdwMAOYYplChtWQUQw span.text-body-small::text').get()
            if location:
                location = location.strip()
            item['location'] = location if location else ''
        except Exception:
            item['location'] = ''
        # try:
        #         location = job.css('div.aMupHxipPGPIbdwMAOYYplChtWQUQw span.text-body-small::text').get()
        #         if location:
        #             location = location.strip()
        #         job_item['company_location'] = location if location else 'not-found'
        #     except Exception:
        #         job_item['company_location'] = 'not-found'

        # Followers and Connections
        for span_text in response.css('span.top-card__subline-item::text').getall():
            if 'followers' in span_text:
                item['followers'] = span_text.replace(' followers', '').strip()
            if 'connections' in span_text:
                item['connections'] = span_text.replace(' connections', '').strip()

        """
            EXPERIENCE SECTION
        """
        item['experience'] = []
        experience_blocks = response.css('li.experience-item')
        for block in experience_blocks:
            experience = {}
            ## organisation profile url
            try:
                experience['organisation_profile'] = block.css('h4 a::attr(href)').get().split('?')[0]
            except Exception as e:
                print('experience --> organisation_profile', e)
                experience['organisation_profile'] = ''
                
                
            ## location
            try:
                experience['location'] = block.css('p.experience-item__location::text').get().strip()
            except Exception as e:
                print('experience --> location', e)
                experience['location'] = ''
                
                
            ## description
            try:
                experience['description'] = block.css('p.show-more-less-text__text--more::text').get().strip()
            except Exception as e:
                print('experience --> description', e)
                try:
                    experience['description'] = block.css('p.show-more-less-text__text--less::text').get().strip()
                except Exception as e:
                    print('experience --> description', e)
                    experience['description'] = ''
                    
            ## time range
            try:
                date_ranges = block.css('span.date-range time::text').getall()
                if len(date_ranges) == 2:
                    experience['start_time'] = date_ranges[0]
                    experience['end_time'] = date_ranges[1]
                    experience['duration'] = block.css('span.date-range__duration::text').get()
                elif len(date_ranges) == 1:
                    experience['start_time'] = date_ranges[0]
                    experience['end_time'] = 'present'
                    experience['duration'] = block.css('span.date-range__duration::text').get()
            except Exception as e:
                print('experience --> time ranges', e)
                experience['start_time'] = ''
                experience['end_time'] = ''
                experience['duration'] = ''
            
            item['experience'].append(experience)

        
        """
            EDUCATION SECTION
        """
        item['education'] = []
        education_blocks = response.css('li.education__list-item')
        for block in education_blocks:
            education = {}

            ## organisation
            try:
                education['organisation'] = block.css('h3::text').get().strip()
            except Exception as e:
                print("education --> organisation", e)
                education['organisation'] = ''


            ## organisation profile url
            try:
                education['organisation_profile'] = block.css('a::attr(href)').get().split('?')[0]
            except Exception as e:
                print("education --> organisation_profile", e)
                education['organisation_profile'] = ''

            ## course details
            try:
                education['course_details'] = ''
                for text in block.css('h4 span::text').getall():
                    education['course_details'] = education['course_details'] + text.strip() + ' '
                education['course_details'] = education['course_details'].strip()
            except Exception as e:
                print("education --> course_details", e)
                education['course_details'] = ''

            ## description
            try:
                education['description'] = block.css('div.education__item--details p::text').get().strip()
            except Exception as e:
                print("education --> description", e)
                education['description'] = ''

         
            ## time range
            try:
                date_ranges = block.css('span.date-range time::text').getall()
                if len(date_ranges) == 2:
                    education['start_time'] = date_ranges[0]
                    education['end_time'] = date_ranges[1]
                elif len(date_ranges) == 1:
                    education['start_time'] = date_ranges[0]
                    education['end_time'] = 'present'
            except Exception as e:
                print("education --> time_ranges", e)
                education['start_time'] = ''
                education['end_time'] = ''

            item['education'].append(education)

        # Insert into MongoDB
        try:
            collection = db['profiles']
            collection.insert_one(item)
            self.logger.info(f"Inserted profile for {item['name']} into MongoDB")
        except Exception as e:
            self.logger.error(f"Failed to insert profile for {item['profile']}: {e}")

        yield item