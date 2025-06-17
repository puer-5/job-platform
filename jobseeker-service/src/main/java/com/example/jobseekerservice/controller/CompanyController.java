package com.example.jobseekerservice.controller;

import com.example.jobseekerservice.entity.Company;
import com.example.jobseekerservice.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // Import PreAuthorize
import org.springframework.security.core.Authentication; // Import Authentication
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    // *** Endpoint to get list of companies with role-based access ***
    // Accessible by USER, ENTERPRISE, and ADMIN roles
    // Unauthenticated users will receive 403 Forbidden because they don't have any roles
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ENTERPRISE', 'ADMIN')")
    public ResponseEntity<List<Company>> getAllCompanies(Authentication authentication) {
        // authentication object contains user info from the header filter
        System.out.println("Accessed /companies - getAllCompanies by user: " + authentication.getName() +
                " with roles: " + authentication.getAuthorities());

        // Call the service method that handles role-based data fetching
        List<Company> companies = companyService.getAllCompaniesForUser();

        return ResponseEntity.ok(companies);
    }

    // Example: Endpoint to create a company (only ENTERPRISE and ADMIN can create)
    @PostMapping
    @PreAuthorize("hasAnyRole('ENTERPRISE', 'ADMIN')")
    public ResponseEntity<Company> createCompany(@RequestBody Company company, Authentication authentication) {
        System.out.println("Accessed /companies - createCompany by user: " + authentication.getName());
        // You might want to add validation and set user ID if companies are linked to users
        Company savedCompany = companyService.createCompany(company);
        return ResponseEntity.status(201).body(savedCompany);
    }

    // Example: Endpoint to verify a company (only ADMIN can verify)
    @PutMapping("/{id}/verify")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Company> verifyCompany(@PathVariable Long id, Authentication authentication) {
        System.out.println("Accessed /companies/" + id + "/verify by user: " + authentication.getName());
        return companyService.verifyCompany(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // You can add other endpoints for getting a single company, updating, deleting etc.
    // and apply @PreAuthorize based on roles and potentially ownership (similar to JobseekerController examples)

}
