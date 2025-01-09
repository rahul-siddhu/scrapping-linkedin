package com.web.scrapping.Controller;
import com.web.scrapping.Entity.Job;
import com.web.scrapping.Service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class JobController {

    @Autowired
    private JobService jobService;

    @GetMapping("/jobs")
    public List<Job> searchJobs(@RequestParam(required = false) String title,
                                @RequestParam(required = false) String company,
                                @RequestParam(required = false) String location) {
        return jobService.searchJobs(title, company, location);
    }

}