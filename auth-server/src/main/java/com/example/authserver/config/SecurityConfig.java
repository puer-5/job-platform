package com.example.authserver.config;


import com.example.authserver.repository.InMemoryUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter; // Import WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain; // Remove this import if not used elsewhere

@Configuration
@EnableWebSecurity // 启用Spring Security的Web安全功能
// *** 继承 WebSecurityConfigurerAdapter ***
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final InMemoryUserRepository userRepository;

    public SecurityConfig(InMemoryUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 使用BCrypt密码编码器
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            // ... (attempting to load user log) ...

            return userRepository.findByUsername(username)
                    .map(user -> {
                        System.out.println("User found in UserDetailsService: " + user.getUsername());
                        System.out.println("Password hash retrieved from repository: " + user.getPasswordHash()); // Log retrieved hash
                        return org.springframework.security.core.userdetails.User
                                .withUsername(user.getUsername())
                                .password(user.getPasswordHash()) // Ensure this is the hashed password
                                .roles("USER")
                                .build();
                    })
                    .orElseThrow(() -> {
                        // ... (user not found log) ...
                        return new UsernameNotFoundException("User not found: " + username);
                    });
        };
    }
//    public UserDetailsService userDetailsService() {
//        // This service is used by DaoAuthenticationProvider to load user details
//        return username ->
//                userRepository.findByUsername(username)
//                .map(user -> org.springframework.security.core.userdetails.User
//                        .withUsername(user.getUsername())
//                        .password(user.getPasswordHash())
//                        // You can add roles/authorities here if your User model has them
//                        // .roles("USER")
//                        .build())
//                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
//    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // *** AuthenticationManager Bean definition can remain as is or be configured via override ***
    // Keeping this as a separate Bean is fine and the newer approach (Spring Security 5.7+)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


    // *** 重写 configure 方法，使用 Spring Security 提供的 HttpSecurity 对象 ***
    @Override // Add @Override annotation
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // 禁用CSRF保护，JWT是无状态的，不需要CSRF
                .authorizeRequests(authorize -> authorize
                        // 允许匿名访问认证相关的端点
                        .antMatchers("/auth/**").permitAll()
                        // 其他所有请求都需要认证 (如果auth-server有其他需要保护的API)
                        // .anyRequest().authenticated()
                        .anyRequest().permitAll() // For simplicity, allow all other requests for now
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // JWT是无状态的，不使用session
                );
        // If you have custom JWT filter for validating tokens on protected endpoints within auth-server
        // http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        // Remove the return statement as configure method is void
        // return http.build();
    }

}
