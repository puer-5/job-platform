package com.example.companyservice.mapper;

import com.example.companyservice.entity.Company;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CompanyMapper {
    Company findById(Long id);
    List<Company> findAll();
    int insert(Company company);
    int update(Company company);
    int delete(Long id);
}
