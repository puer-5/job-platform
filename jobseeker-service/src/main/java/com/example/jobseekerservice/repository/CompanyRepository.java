package com.example.jobseekerservice.repository;

import com.example.jobseekerservice.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    // Spring Data JPA will automatically create a query to find companies where 'verified' is true
    List<Company> findByVerifiedTrue();

    // You might also want to find unverified companies for admin/enterprise review
    List<Company> findByVerifiedFalse();

    // findById and findAll are provided by JpaRepository
}
