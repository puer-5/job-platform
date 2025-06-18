package com.example.adminservice.entity;

import lombok.Data;

import java.util.Date;


@Data
public class CompanyDTO {
    private Long id;
    private String name;
    private String industry;
    private String address;
    private String website;
    private String description;
    private Boolean verified;
    private Date createdAt;
}
