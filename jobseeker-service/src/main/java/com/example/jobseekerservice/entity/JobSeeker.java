package com.example.jobseekerservice.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.util.Date;

@Data
@TableName("job_seeker")
public class JobSeeker {

    @TableId(type = IdType.INPUT)
    private Long id;

    private String fullName;
    private String gender;
    private Date birthDate;
    private String educationLevel;
    private Integer workExperience;
    private String skills; // JSON 类型，建议转为字符串或自定义序列化类
    private String profileStatus;
}
