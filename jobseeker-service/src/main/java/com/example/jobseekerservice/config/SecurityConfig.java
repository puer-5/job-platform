package com.example.jobseekerservice.config;


import com.example.jobseekerservice.security.UserHeaderAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity; // Import for method security
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; // Import for filter order

@Configuration
@EnableWebSecurity
// *** Enable method-level security using @PreAuthorize, @PostAuthorize, etc. ***
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // Register our custom filter as a Bean
    @Bean
    public UserHeaderAuthenticationFilter userHeaderAuthenticationFilter() {
        return new UserHeaderAuthenticationFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Disable CSRF for stateless API
                .authorizeRequests()
                // Define URL-based access rules here if needed
                // For example:
                // .antMatchers("/public/**").permitAll() // Public endpoints
                // .antMatchers("/admin/**").hasRole("ADMIN") // Admin specific endpoints
                // .antMatchers("/jobseekers").hasAnyRole("USER", "ENTERPRISE", "ADMIN") // Example: All authenticated users can list jobseekers
                .anyRequest().permitAll() // Allow all requests to reach controllers, let method security handle authorization
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Use stateless sessions

        // Add our filter to populate SecurityContextHolder from headers
        // Execute it early in the filter chain, before other standard authentication filters
        http.addFilterBefore(userHeaderAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        // Disable default Spring Security login page, logout, etc.
        http.httpBasic().disable();
        http.formLogin().disable();
        http.logout().disable();
    }
}
