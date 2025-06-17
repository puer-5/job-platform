package com.example.companyservice.entity;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class Company {
    private Long id;
    private String name;
    private String industry;
    private String address;
    private String website;
    private String description;
    private Boolean verified;
    private Timestamp createdAt;
}
