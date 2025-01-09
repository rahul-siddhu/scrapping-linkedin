package com.web.scrapping.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "jobs")
public class Job {

    @Id
    private String id;

    @Field("job_title")
    private String jobTitle;

    @Field("job_detail_url")
    private String jobDetailUrl;

    @Field("job_listed")
    private String jobListed;

    @Field("company_name")
    private String companyName;

    @Field("company_link")
    private String companyLink;

    @Field("company_location")
    private String companyLocation;

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobDetailUrl() {
        return jobDetailUrl;
    }

    public void setJobDetailUrl(String jobDetailUrl) {
        this.jobDetailUrl = jobDetailUrl;
    }

    public String getJobListed() {
        return jobListed;
    }

    public void setJobListed(String jobListed) {
        this.jobListed = jobListed;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyLink() {
        return companyLink;
    }

    public void setCompanyLink(String companyLink) {
        this.companyLink = companyLink;
    }

    public String getCompanyLocation() {
        return companyLocation;
    }

    public void setCompanyLocation(String companyLocation) {
        this.companyLocation = companyLocation;
    }
}
