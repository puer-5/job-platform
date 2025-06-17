package com.example.companyservice.entity;

import lombok.Data;
import java.sql.Timestamp;
import java.util.Date;

@Data
public class JobPost {
    private Long id;
    private Long companyId;
    private String title;
    private String description;
    private String requirements;
    private String location;
    private String salaryRange;
    private String jobType;   // FULL_TIME, PART_TIME, CONTRACT, INTERNSHIP
    private String status;    // OPEN, CLOSED, DRAFT
    private Timestamp postedDate;
    private Date expiryDate;
}
