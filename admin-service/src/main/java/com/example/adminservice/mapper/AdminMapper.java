package com.example.adminservice.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper {
    int countCompanies();
    int countJobSeekers();
    int countJobPosts();
    int countTotalApplications();
}
