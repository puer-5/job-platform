package com.example.jobseekerservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.jobseekerservice.entity.Resume;
import com.example.jobseekerservice.mapper.ResumeMapper;
import com.example.jobseekerservice.service.ResumeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ResumeServiceImpl extends ServiceImpl<ResumeMapper, Resume> implements ResumeService {

    @Override
    public List<Resume> getResumesByJobSeekerId(Long jobSeekerId) {
        return lambdaQuery().eq(Resume::getJobSeekerId, jobSeekerId).list();
    }

    @Override
    @Transactional
    public boolean setDefaultResume(Long resumeId) {
        Resume resume = getById(resumeId);
        if (resume == null) return false;

        // 先将当前用户的所有简历默认值设为 false
        LambdaUpdateWrapper<Resume> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Resume::getJobSeekerId, resume.getJobSeekerId())
                .set(Resume::getIsDefault, false);
        this.update(updateWrapper);

        // 然后将选中的 resume 设置为默认
        Resume toUpdate = new Resume();
        toUpdate.setId(resumeId);
        toUpdate.setIsDefault(true);
        return updateById(toUpdate);
    }
}