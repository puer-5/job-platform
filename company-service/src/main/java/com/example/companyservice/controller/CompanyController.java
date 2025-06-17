package com.example.companyservice.controller;

import com.example.companyservice.entity.Company;
import com.example.companyservice.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/{id}")
    public Company getCompany(@PathVariable Long id) {
        return companyService.getById(id);
    }

    @GetMapping
    public List<Company> getAllCompanies() {
        return companyService.getAll();
    }

    @PostMapping
    public String createCompany(@RequestBody Company company) {
        return companyService.createCompany(company) ? "Created successfully" : "Failed to create";
    }

    @PutMapping
    public String updateCompany(@RequestBody Company company) {
        return companyService.updateCompany(company) ? "Updated successfully" : "Failed to update";
    }

    @DeleteMapping("/{id}")
    public String deleteCompany(@PathVariable Long id) {
        return companyService.deleteCompany(id) ? "Deleted successfully" : "Failed to delete";
    }
}
