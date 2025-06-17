package com.example.companyservice.service;


import com.example.companyservice.entity.JobPost;

import java.util.List;

public interface JobPostService {
    int addJobPost(JobPost jobPost);
    JobPost getJobPostById(Long id);
    List<JobPost> getAllJobPosts();
    int updateJobPost(JobPost jobPost);
    int deleteJobPost(Long id);
    List<JobPost> getJobPostsByCompanyId(Long companyId);
}
