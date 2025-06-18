package com.example.adminservice.entity;

import lombok.Data;

import java.util.Date;

@Data
public class JobSeekerDTO {
    private Long id;
    private String fullName;
    private String gender;
    private Date birthDate;
    private String educationLevel;
    private Integer workExperience;
    private String skills;
    private String profileStatus;
}
