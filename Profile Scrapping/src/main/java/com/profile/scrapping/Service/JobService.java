package com.profile.scrapping.Service;

import com.profile.scrapping.Entity.Profile;

//import org.springframework.context.annotation.Profile;
import com.profile.scrapping.Repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    public List<Profile> searchProfiles(String profile, String name, String location) {
        if ((profile == null || profile.isEmpty()) &&
                (name == null || name.isEmpty()) &&
                (location == null || location.isEmpty())) {
            // Return all jobs if no search parameters are provided
            return jobRepository.findAll();
        }

        // Custom filtering logic
        return jobRepository.findAll() // Get all jobs
                .stream()
                .filter(job -> profile == null || job.getProfile().toLowerCase().contains(profile.toLowerCase()))
                .filter(job -> name == null || job.getName().toLowerCase().contains(name.toLowerCase()))
                .filter(job -> location == null || job.getLocation().toLowerCase().contains(location.toLowerCase()))
                .toList();
    }
}
