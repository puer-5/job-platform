package com.example.zuulgateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Disable CSRF for stateless API
                .authorizeRequests()
                // Allow access to auth service endpoints
                .antMatchers("/auth/**").permitAll()
                // All other requests require authentication (handled by the filter)
                // .anyRequest().authenticated() // Option 1: Require authentication for all other requests
                .anyRequest().permitAll() // Option 2: Allow all requests, authorization is handled downstream
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Use stateless sessions

        // Disable default Spring Security features not needed for a stateless API gateway
        http.httpBasic().disable();
        http.formLogin().disable();
        http.logout().disable();

    }
}
