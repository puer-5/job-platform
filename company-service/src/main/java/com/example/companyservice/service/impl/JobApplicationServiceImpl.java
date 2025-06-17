package com.example.companyservice.service.impl;

import com.example.companyservice.entity.JobApplication;
import com.example.companyservice.mapper.JobApplicationMapper;
import com.example.companyservice.service.JobApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobApplicationServiceImpl implements JobApplicationService {

    @Autowired
    private JobApplicationMapper applicationMapper;

    @Override
    public List<JobApplication> getApplicationsByCompanyId(Long companyId) {
        return applicationMapper.getApplicationsByCompanyId(companyId);
    }

    @Override
    public List<JobApplication> getApplicationsByJobPostId(Long jobPostId) {
        return applicationMapper.getApplicationsByJobPostId(jobPostId);
    }

    @Override
    public boolean updateStatus(Long applicationId, String status) {
        return applicationMapper.updateApplicationStatus(applicationId, status) > 0;
    }
}
