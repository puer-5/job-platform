package com.example.companyservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "company")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(length = 100)
    private String industry;

    @Column(length = 255)
    private String address;

    @Column(length = 255)
    private String website;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private boolean verified = false; // Default to false

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    // Add a constructor for creating new companies (without ID, timestamps)
    public Company(String name, String industry, String address, String website, String description, boolean verified) {
        this.name = name;
        this.industry = industry;
        this.address = address;
        this.website = website;
        this.description = description;
        this.verified = verified;
    }
}