package com.example.jobseekerservice.entity;

import jakarta.persistence.*;

@Entity
public class JobSeeker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String skills;

    // Getters and Setters
}
