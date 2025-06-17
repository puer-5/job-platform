package com.example.authserver.service;

import com.example.authserver.dto.UserDetailsDto; // Import UserDetailsDto
import com.example.authserver.model.User;
import com.example.authserver.util.JwtUtil;
import com.example.authserver.model.UserType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional; // Import Optional

@Service
public class AuthService {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserService userService, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public String login(String username, String password) throws AuthenticationException {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        authenticationManager.authenticate(authenticationToken); // Authentication happens here

        // If authentication successful, find the user and generate token
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found after authentication?"));

        // Generate JWT
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId().toString()); // Store ID as String in JWT
        claims.put("username", user.getUsername()); // Store username in JWT (optional, but useful)
        claims.put("userType", user.getUserType().name()); // Store user type name in JWT

        return jwtUtil.generateToken(user.getUsername(), claims);
    }

    // Method for downstream services to validate token and get user info
    public UserDetailsDto validateTokenAndGetUserDetails(String token) {
        if (jwtUtil.validateToken(token)) {
            String username = jwtUtil.extractUsername(token);
            // Extract additional claims
            String userId = jwtUtil.getUserIdFromToken(token); // Assumes userId is in claims as String
            String userTypeString = jwtUtil.extractClaim(token, claims -> claims.get("userType", String.class));

            // Convert userTypeString back to Enum if needed, or just pass the string
            // For simplicity, returning the string name
            // UserType userType = UserType.valueOf(userTypeString); // If you need the Enum object

            // Fetch email if needed (not stored in token by default, requires DB lookup)
            // Optional<User> userOptional = userService.findById(Long.parseLong(userId));
            // String email = userOptional.map(User::getEmail).orElse(null);


            // Return basic info from token claims
            return new UserDetailsDto(userId, username, null, UserType.valueOf(userTypeString)); // Assuming email is not in token and not fetched
        }
        return null; // Token invalid or expired
    }

    // Simple validation method returning boolean
    public boolean isValidToken(String token) {
        return jwtUtil.validateToken(token);
    }
}
