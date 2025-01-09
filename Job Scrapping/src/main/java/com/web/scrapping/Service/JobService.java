package com.web.scrapping.Service;

import com.web.scrapping.Entity.Job;
import com.web.scrapping.Repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    public List<Job> searchJobs(String title, String company, String location) {
        if ((title == null || title.isEmpty()) &&
                (company == null || company.isEmpty()) &&
                (location == null || location.isEmpty())) {
            // Return all jobs if no search parameters are provided
            return jobRepository.findAll();
        }

        // Custom filtering logic
        return jobRepository.findAll() // Get all jobs
                .stream()
                .filter(job -> title == null || job.getJobTitle().toLowerCase().contains(title.toLowerCase()))
                .filter(job -> company == null || job.getCompanyName().toLowerCase().contains(company.toLowerCase()))
                .filter(job -> location == null || job.getCompanyLocation().toLowerCase().contains(location.toLowerCase()))
                .toList();
    }
}
