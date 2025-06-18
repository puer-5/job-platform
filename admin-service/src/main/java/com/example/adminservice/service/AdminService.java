package com.example.adminservice.service;

import com.example.adminservice.entity.StatisticsDTO;

public interface AdminService {
    StatisticsDTO getStatistics();
    long getCompanyCount();
    long getJobSeekerCount();
    long getJobPostCount();
    long getApplicationCount();
}
