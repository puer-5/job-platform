package com.example.authserver.service;

import com.example.authserver.exception.UserAlreadyExistsException;
import com.example.authserver.model.User;
import com.example.authserver.repository.InMemoryUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final InMemoryUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(InMemoryUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerNewUser(String username, String password, String email) throws UserAlreadyExistsException {
        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistsException("User with username " + username + " already exists");
        }

        User newUser = new User();
        newUser.setUsername(username);
        String hashedPassword = passwordEncoder.encode(password);
        newUser.setPasswordHash(hashedPassword); // Hash the password
        newUser.setEmail(email);
        // Set other default properties if needed
        System.out.println("Registering user: " + username);
        System.out.println("Original password: " + password); // Be cautious with logging plain passwords in production
        System.out.println("Hashed password: " + hashedPassword); // Log the hashed password

        return userRepository.save(newUser);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findById(String userId) {
        return userRepository.findById(userId);
    }

    // Add other user-related methods here (e.g., update profile, change password - requires database)
}
