package com.example.companyservice.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("job_application")
public class JobApplication {
    private Long id;
    private Long jobPostId;
    private Long jobSeekerId;
    private Long resumeId;
    private String status; // PENDING, REVIEWED, REJECTED, INTERVIEW, OFFERED, HIRED
    private Timestamp appliedDate;
    private Timestamp lastUpdated;
}
