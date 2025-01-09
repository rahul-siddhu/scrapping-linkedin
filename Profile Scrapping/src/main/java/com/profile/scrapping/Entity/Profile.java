package com.profile.scrapping.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "profiles")
public class Profile {

    @Id
    private String id;

    private String profile;

    private String url;

    private String name;

    private String description;

    private String location;

    private String followers;

    private String connections;

    private String about;

    @Field("experience")
    private List<Experience> experience;

    @Field("education")
    private List<Education> education;

    @Field("scraped_at")
    private LocalDateTime scrapedAt;

    // Getters and Setters

    // Inner class for Experience
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Experience {
        private String organisation_profile;
        private String location;
        private String description;
        private String start_time;
        private String end_time;
        private String duration;

        // Getters and Setters
    }

    // Inner class for Education
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Education {
        private String organisation;
        private String organisation_profile;
        private String course_details;
        private String description;
        private String start_time;
        private String end_time;

        // Getters and Setters
    }

    // Getters and Setters for Profile class
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public String getConnections() {
        return connections;
    }

    public void setConnections(String connections) {
        this.connections = connections;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public List<Experience> getExperience() {
        return experience;
    }

    public void setExperience(List<Experience> experience) {
        this.experience = experience;
    }

    public List<Education> getEducation() {
        return education;
    }

    public void setEducation(List<Education> education) {
        this.education = education;
    }

    public LocalDateTime getScrapedAt() {
        return scrapedAt;
    }

    public void setScrapedAt(LocalDateTime scrapedAt) {
        this.scrapedAt = scrapedAt;
    }
}
