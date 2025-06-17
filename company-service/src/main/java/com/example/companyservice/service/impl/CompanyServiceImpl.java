package com.example.companyservice.service.impl;

import com.example.companyservice.entity.Company;
import com.example.companyservice.mapper.CompanyMapper;
import com.example.companyservice.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyMapper companyMapper;

    @Override
    public Company getById(Long id) {
        return companyMapper.findById(id);
    }

    @Override
    public List<Company> getAll() {
        return companyMapper.findAll();
    }

    @Override
    public boolean createCompany(Company company) {
        return companyMapper.insert(company) > 0;
    }

    @Override
    public boolean updateCompany(Company company) {
        return companyMapper.update(company) > 0;
    }

    @Override
    public boolean deleteCompany(Long id) {
        return companyMapper.delete(id) > 0;
    }
}
