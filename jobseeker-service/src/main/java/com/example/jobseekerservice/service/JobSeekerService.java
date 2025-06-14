package com.example.jobseekerservice.service;


import com.example.jobseekerservice.entity.JobSeeker;
import com.example.jobseekerservice.repository.JobSeekerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobSeekerService {

    private final JobSeekerRepository repository;

    public JobSeekerService(JobSeekerRepository repository) {
        this.repository = repository;
    }

    public List<JobSeeker> findAll() {
        return repository.findAll();
    }

    public Optional<JobSeeker> findById(Long id) {
        return repository.findById(id);
    }

    public JobSeeker save(JobSeeker jobSeeker) {
        return repository.save(jobSeeker);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
