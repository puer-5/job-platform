package com.example.jobseekerservice.controller;

import com.example.jobseekerservice.entity.Resume;
import com.example.jobseekerservice.service.ResumeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resumes")
@Api(tags = "简历 Resume 接口")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @ApiOperation("获取某个求职者的全部简历")
    @GetMapping("/jobseeker/{jobSeekerId}")
    public List<Resume> getByJobSeeker(@PathVariable Long jobSeekerId) {
        return resumeService.getResumesByJobSeekerId(jobSeekerId);
    }

    @ApiOperation("新增简历")
    @PostMapping
    public boolean createResume(@RequestBody Resume resume) {
        return resumeService.save(resume);
    }

    @ApiOperation("更新简历")
    @PutMapping("/{id}")
    public boolean updateResume(@PathVariable Long id, @RequestBody Resume resume) {
        resume.setId(id);
        return resumeService.updateById(resume);
    }

    @ApiOperation("删除简历")
    @DeleteMapping("/{id}")
    public boolean deleteResume(@PathVariable Long id) {
        return resumeService.removeById(id);
    }

    @ApiOperation("将某份简历设置为默认简历")
    @PostMapping("/{id}/set-default")
    public boolean setDefaultResume(@PathVariable Long id) {
        return resumeService.setDefaultResume(id);
    }
}
