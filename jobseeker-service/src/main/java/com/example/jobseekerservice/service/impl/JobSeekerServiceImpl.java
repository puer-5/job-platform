package com.example.jobseekerservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.jobseekerservice.entity.JobSeeker;
import com.example.jobseekerservice.mapper.JobSeekerMapper;
import com.example.jobseekerservice.service.JobSeekerService;
import org.springframework.stereotype.Service;

@Service
public class JobSeekerServiceImpl extends ServiceImpl<JobSeekerMapper, JobSeeker> implements JobSeekerService {
    // 可扩展自定义业务逻辑
}
