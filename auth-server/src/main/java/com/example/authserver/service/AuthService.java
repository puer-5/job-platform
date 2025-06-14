package com.example.authserver.service;

import com.example.authserver.dto.UserDetailsDto;
import com.example.authserver.model.User;
import com.example.authserver.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager; // Spring Security's AuthenticationManager

    public AuthService(UserService userService, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public String login(String username, String password) throws AuthenticationException {
        System.out.println("AuthService - Attempting login for username: " + username);
        // *** 添加日志，打印传入的明文密码 (生产环境请勿这样做！) ***
        System.out.println("AuthService - Received plaintext password: " + password);
        // Use Spring Security's AuthenticationManager to validate credentials
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        // If authentication successful, find the user and generate token
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found after authentication?")); // Should not happen if authenticate succeeded

        // Generate JWT
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId()); // Add user ID to claims
        // Add roles or other relevant info to claims if needed
        // claims.put("roles", user.getRoles());

        return jwtUtil.generateToken(user.getUsername(), claims);
    }

    // Method for downstream services to validate token and get user info
    public UserDetailsDto validateTokenAndGetUserDetails(String token) {
        if (jwtUtil.validateToken(token)) {
            String username = jwtUtil.extractUsername(token);
            String userId = jwtUtil.getUserIdFromToken(token); // Get user ID from token claims

            // Optionally retrieve more user details from UserService/Repository
            // This step might be skipped if all necessary info is in the token claims
            // Optional<User> userOptional = userService.findByUsername(username);
            // if (userOptional.isPresent()) {
            //     User user = userOptional.get();
            //     return new UserDetailsDto(user.getId(), user.getUsername(), user.getEmail(), /* roles */);
            // }

            // Return basic info from token claims
            return new UserDetailsDto(userId, username, null /* email not in token */);

        }
        return null; // Token invalid or expired
    }

    // Simple validation method returning boolean
    public boolean isValidToken(String token) {
        return jwtUtil.validateToken(token);
    }
}
