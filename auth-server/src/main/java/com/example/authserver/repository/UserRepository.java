package com.example.authserver.repository;

import com.example.authserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // Optional, but good practice
public interface UserRepository extends JpaRepository<User, Long> { // User entity, ID type is Long

    // Spring Data JPA automatically implements these based on method names
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username); // Use Boolean for existence checks

    Optional<User> findByEmail(String email); // Add findByEmail as email is unique

    Boolean existsByEmail(String email); // Add existsByEmail

    // findById is already provided by JpaRepository
}
