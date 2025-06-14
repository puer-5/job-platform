package com.example.jobseekerservice.repository;


import com.example.jobseekerservice.entity.JobSeeker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobSeekerRepository extends JpaRepository<JobSeeker, Long> {
}
