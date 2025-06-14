package com.example.jobseekerservice.controller;

import com.example.jobseekerservice.entity.JobSeeker;
import com.example.jobseekerservice.service.JobSeekerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// JobSeekerController.java
@RestController
@RequestMapping("/jobseekers")
public class JobSeekerController {

    private final JobSeekerService service;

    public JobSeekerController(JobSeekerService service) {
        this.service = service;
    }

    @GetMapping
    public List<JobSeeker> listAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public JobSeeker getById(@PathVariable Long id) {
        return service.findById(id).orElse(null);
    }

    @PostMapping
    public JobSeeker create(@RequestBody JobSeeker jobSeeker) {
        return service.save(jobSeeker);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello from JobSeeker Service!";
    }
}
