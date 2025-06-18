package com.example.adminservice.entity;

import lombok.Data;

@Data
public class StatisticsDTO {
    private int companyCount;
    private int jobSeekerCount;
    private int jobPostCount;
    private int applicationCount;
}
