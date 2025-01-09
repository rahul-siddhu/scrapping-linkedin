package com.profile.scrapping.Repository;

import com.profile.scrapping.Entity.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends MongoRepository<Profile, String> {
    // Custom query methods (optional) can be added here if needed
}
