package com.example.authserver.repository;

import com.example.authserver.model.User;
// import org.springframework.security.crypto.password.PasswordEncoder; // Remove this import
import org.springframework.stereotype.Repository;

// import javax.annotation.PostConstruct; // Remove this import

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryUserRepository {

    private final Map<String, User> users = new ConcurrentHashMap<>();
    // Remove: private final PasswordEncoder passwordEncoder;

    // Constructor Injection - No need for PasswordEncoder here anymore
    public InMemoryUserRepository() {
        // Remove dependency on PasswordEncoder
    }


    // Remove @PostConstruct method for dummy user initialization
    // @PostConstruct
    // public void init() {
    //     // ... initialization code ...
    // }


    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(users.get(username));
    }

    public User save(User user) {
        // In a real scenario, handle ID generation properly and check for duplicates
//        if (user.getId() == null) {
//            user.setId(UUID.randomUUID().toString());
//        }
        // Password is expected to be already hashed when passed to save
        users.put(user.getUsername(), user);
        System.out.println("User saved to in-memory repository: " + user.getUsername());
        return user;
    }

    public boolean existsByUsername(String username) {
        return users.containsKey(username);
    }

    // For demonstration: get user by ID (useful after token validation)
    public Optional<User> findById(String id) {
        return users.values().stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }
}
