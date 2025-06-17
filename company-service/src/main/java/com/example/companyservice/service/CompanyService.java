package com.example.companyservice.service;

import com.example.companyservice.entity.Company;
import java.util.List;

public interface CompanyService {
    Company getById(Long id);
    List<Company> getAll();
    boolean createCompany(Company company);
    boolean updateCompany(Company company);
    boolean deleteCompany(Long id);
}
