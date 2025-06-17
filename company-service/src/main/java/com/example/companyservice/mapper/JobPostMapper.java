package com.example.companyservice.mapper;

import com.example.companyservice.entity.JobPost;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface JobPostMapper {

    @Insert("INSERT INTO job_post (company_id, title, description, requirements, location, salary_range, job_type, status, posted_date, expiry_date) " +
            "VALUES (#{companyId}, #{title}, #{description}, #{requirements}, #{location}, #{salaryRange}, #{jobType}, #{status}, #{postedDate}, #{expiryDate})")
    int insertJobPost(JobPost jobPost);

    @Select("SELECT * FROM job_post WHERE id = #{id}")
    JobPost getJobPostById(Long id);

    @Select("SELECT * FROM job_post")
    List<JobPost> getAllJobPosts();

    @Update("UPDATE job_post SET title = #{title}, description = #{description}, requirements = #{requirements}, " +
            "location = #{location}, salary_range = #{salaryRange}, job_type = #{jobType}, status = #{status}, " +
            "posted_date = #{postedDate}, expiry_date = #{expiryDate} WHERE id = #{id}")
    int updateJobPost(JobPost jobPost);

    @Delete("DELETE FROM job_post WHERE id = #{id}")
    int deleteJobPost(Long id);

    @Select("SELECT * FROM job_post WHERE company_id = #{companyId}")
    List<JobPost> getJobPostsByCompanyId(Long companyId);
}
