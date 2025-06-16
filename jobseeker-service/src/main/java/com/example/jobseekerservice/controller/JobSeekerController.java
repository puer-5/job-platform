package com.example.jobseekerservice.controller;

import com.example.jobseekerservice.entity.JobSeeker;
import com.example.jobseekerservice.service.JobSeekerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// JobSeekerController.java
@RestController
@RequestMapping("/admin/jobseekers")
@Api(tags = "求职者 JobSeeker 接口（管理员）")
public class JobSeekerController {

    @Autowired
    private JobSeekerService jobSeekerService;

    @ApiOperation("获取全部求职者")
    @GetMapping
    public List<JobSeeker> getAllJobSeekers() {
        return jobSeekerService.list();
    }

    @ApiOperation("根据ID获取求职者")
    @GetMapping("/{id}")
    public JobSeeker getJobSeeker(@PathVariable Long id) {
        return jobSeekerService.getById(id);
    }

    @ApiOperation("新增求职者")
    @PostMapping
    public boolean createJobSeeker(@RequestBody JobSeeker jobSeeker) {
        return jobSeekerService.save(jobSeeker);
    }

    @ApiOperation("更新求职者信息")
    @PutMapping("/{id}")
    public boolean updateJobSeeker(@PathVariable Long id, @RequestBody JobSeeker jobSeeker) {
        jobSeeker.setId(id);
        return jobSeekerService.updateById(jobSeeker);
    }

    @ApiOperation("删除求职者")
    @DeleteMapping("/{id}")
    public boolean deleteJobSeeker(@PathVariable Long id) {
        return jobSeekerService.removeById(id);
    }
}
