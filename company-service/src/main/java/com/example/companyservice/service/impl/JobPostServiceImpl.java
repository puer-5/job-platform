package com.example.companyservice.service.impl;

import com.example.companyservice.entity.JobPost;
import com.example.companyservice.mapper.JobPostMapper;
import com.example.companyservice.service.JobPostService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class JobPostServiceImpl implements JobPostService {

    @Resource
    private JobPostMapper jobPostMapper;

    @Override
    public int addJobPost(JobPost jobPost) {
        return jobPostMapper.insertJobPost(jobPost);
    }

    @Override
    public JobPost getJobPostById(Long id) {
        return jobPostMapper.getJobPostById(id);
    }

    @Override
    public List<JobPost> getAllJobPosts() {
        return jobPostMapper.getAllJobPosts();
    }

    @Override
    public int updateJobPost(JobPost jobPost) {
        return jobPostMapper.updateJobPost(jobPost);
    }

    @Override
    public int deleteJobPost(Long id) {
        return jobPostMapper.deleteJobPost(id);
    }

    @Override
    public List<JobPost> getJobPostsByCompanyId(Long companyId) {
        return jobPostMapper.getJobPostsByCompanyId(companyId);
    }
}
