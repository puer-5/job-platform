package com.example.jobseekerservice.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("resume")
public class Resume {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long jobSeekerId;
    private String title;
    private String experienceSummary;
    private String educationDetails; // JSON 字符串
    private String skills;           // JSON 字符串

    private Timestamp createdAt;
    private Boolean isDefault;
}
