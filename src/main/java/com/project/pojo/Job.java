package com.project.pojo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobId;

    @Column(name = "organization")
    private String organization;

    @Column(name = "job_designation")
    private String jobDesignation;

    @Column(name = "job_description", length = 1000)  // Assuming job descriptions might be lengthy
    private String jobDescription;

    // Constructors
    public Job() {
        super();
    }
    
    @ManyToMany(mappedBy = "jobs")
    private Set<User> users;

    // Getters and setters
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
    

    // Getters and Setters
    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getJobDesignation() {
        return jobDesignation;
    }

    public void setJobDesignation(String jobDesignation) {
        this.jobDesignation = jobDesignation;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    // toString method for debugging and logging
    @Override
    public String toString() {
        return "Job [jobId=" + jobId + ", organization=" + organization + ", jobDesignation=" + jobDesignation
                + ", jobDescription=" + jobDescription + "]";
    }
}
