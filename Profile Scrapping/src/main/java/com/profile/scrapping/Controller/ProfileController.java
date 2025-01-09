package com.profile.scrapping.Controller;
import com.profile.scrapping.Entity.Profile;
import com.profile.scrapping.Service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProfileController {

    @Autowired
    private JobService jobService;

    @GetMapping("/profiles")
    public List<Profile> searchJobs(@RequestParam(required = false) String profile,
                                    @RequestParam(required = false) String name,
                                    @RequestParam(required = false) String location) {
        return jobService.searchProfiles(profile, name, location);
    }

}