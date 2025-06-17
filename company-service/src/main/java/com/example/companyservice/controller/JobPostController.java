package com.example.companyservice.controller;

import com.example.companyservice.entity.JobPost;
import com.example.companyservice.service.JobPostService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/jobposts")
public class JobPostController {

    @Resource
    private JobPostService jobPostService;

    @PostMapping
    public String addJobPost(@RequestBody JobPost jobPost) {
        return jobPostService.addJobPost(jobPost) > 0 ? "Success" : "Failed";
    }

    @GetMapping("/{id}")
    public JobPost getJobPostById(@PathVariable Long id) {
        return jobPostService.getJobPostById(id);
    }

    @GetMapping
    public List<JobPost> getAllJobPosts() {
        return jobPostService.getAllJobPosts();
    }

    @PutMapping
    public String updateJobPost(@RequestBody JobPost jobPost) {
        return jobPostService.updateJobPost(jobPost) > 0 ? "Updated" : "Failed";
    }

    @DeleteMapping("/{id}")
    public String deleteJobPost(@PathVariable Long id) {
        return jobPostService.deleteJobPost(id) > 0 ? "Deleted" : "Failed";
    }

    @GetMapping("/company/{companyId}")
    public List<JobPost> getJobPostsByCompanyId(@PathVariable Long companyId) {
        return jobPostService.getJobPostsByCompanyId(companyId);
    }
}
