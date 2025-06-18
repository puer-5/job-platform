package com.example.adminservice.service.impl;

import com.example.adminservice.entity.StatisticsDTO;
import com.example.adminservice.mapper.AdminMapper;
import com.example.adminservice.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public StatisticsDTO getStatistics() {
        StatisticsDTO dto = new StatisticsDTO();
        dto.setCompanyCount(adminMapper.countCompanies());
        dto.setJobSeekerCount(adminMapper.countJobSeekers());
        dto.setJobPostCount(adminMapper.countJobPosts());
        dto.setApplicationCount(adminMapper.countTotalApplications());
        return dto;
    }
    @Override
    public long getCompanyCount() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM company", Long.class);
    }

    @Override
    public long getJobSeekerCount() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM job_seeker", Long.class);
    }

    @Override
    public long getJobPostCount() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM job_post", Long.class);
    }

    @Override
    public long getApplicationCount() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM job_application", Long.class);
    }
}
