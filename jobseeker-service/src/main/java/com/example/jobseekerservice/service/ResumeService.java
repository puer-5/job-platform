package com.example.jobseekerservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.jobseekerservice.entity.Resume;

import java.util.List;

public interface ResumeService extends IService<Resume> {

    List<Resume> getResumesByJobSeekerId(Long jobSeekerId);
    boolean setDefaultResume(Long resumeId);
}