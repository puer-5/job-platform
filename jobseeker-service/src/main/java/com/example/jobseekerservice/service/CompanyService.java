package com.example.jobseekerservice.service;

import com.example.jobseekerservice.entity.Company;
import com.example.jobseekerservice.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication; // Import Authentication
import org.springframework.security.core.GrantedAuthority; // Import GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder; // Import SecurityContextHolder
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Import Transactional

import java.util.Collection; // Import Collection
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    // *** Method to get companies based on authenticated user's role ***
    public List<Company> getAllCompaniesForUser() {
        // Get the current authentication object from SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if the user has ADMIN or ENTERPRISE role
        boolean isAdminOrEnterprise = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN") || auth.getAuthority().equals("ROLE_ENTERPRISE"));

        if (isAdminOrEnterprise) {
            // ADMIN and ENTERPRISE users see all companies
            System.out.println("CompanyService: Fetching all companies for ADMIN/ENTERPRISE user: " + authentication.getName());
            return companyRepository.findAll();
        } else {
            // Regular USERs and potentially others see only verified companies
            System.out.println("CompanyService: Fetching only verified companies for USER: " + authentication.getName());
            return companyRepository.findByVerifiedTrue();
        }
    }

    // Optional: Method to get all companies (for internal use or specific admin endpoint)
    @Transactional(readOnly = true)
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    // Optional: Method to get only verified companies
    @Transactional(readOnly = true)
    public List<Company> findAllVerified() {
        return companyRepository.findByVerifiedTrue();
    }


    // Example: Get a single company by ID
    @Transactional(readOnly = true)
    public Optional<Company> findById(Long id) {
        return companyRepository.findById(id);
    }

    // Example: Create a new company (might require specific roles, e.g., ENTERPRISE or ADMIN)
    @Transactional
    public Company createCompany(Company company) {
        // Add logic here to set created_at/updated_at if not using annotations,
        // but annotations are preferred with JPA.

        // You might want to set 'verified' to false by default for new companies
        company.setVerified(false);

        Company savedCompany = companyRepository.save(company);
        System.out.println("Company created: " + savedCompany.getName());
        return savedCompany;
    }

    // Example: Update a company
    @Transactional
    public Optional<Company> updateCompany(Long id, Company companyDetails) {
        return companyRepository.findById(id)
                .map(company -> {
                    company.setName(companyDetails.getName());
                    company.setIndustry(companyDetails.getIndustry());
                    company.setAddress(companyDetails.getAddress());
                    company.setWebsite(companyDetails.getWebsite());
                    company.setDescription(companyDetails.getDescription());
                    // Decide if 'verified' can be updated here or via a separate endpoint
                    // company.setVerified(companyDetails.isVerified());
                    Company updatedCompany = companyRepository.save(company);
                    System.out.println("Company updated: " + updatedCompany.getName());
                    return updatedCompany;
                });
    }

    // Example: Verify a company (might require ADMIN role)
    @Transactional
    public Optional<Company> verifyCompany(Long id) {
        return companyRepository.findById(id)
                .map(company -> {
                    company.setVerified(true);
                    Company verifiedCompany = companyRepository.save(company);
                    System.out.println("Company verified: " + verifiedCompany.getName());
                    return verifiedCompany;
                });
    }

    // Example: Delete a company (might require ADMIN role)
    @Transactional
    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
        System.out.println("Company deleted with ID: " + id);
    }


}
