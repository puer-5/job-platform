package com.example.adminservice.client;

import com.example.jobseekerservice.entity.JobSeeker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "jobseeker-service")
public interface JobSeekerClient {
    @GetMapping("/api/jobseeker")
    List<JobSeeker> getAllJobSeekers();
}
