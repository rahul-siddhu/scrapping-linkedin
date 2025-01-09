package com.web.scrapping.Repository;

import com.web.scrapping.Entity.Job;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends MongoRepository<Job, String> {
    // Custom query methods (optional) can be added here if needed
}
