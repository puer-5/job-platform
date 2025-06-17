package com.example.authserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*; // Import JPA annotations
import java.time.LocalDateTime; // Import LocalDateTime
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity // Declares this class as a JPA entity
@Table(name = "user") // Maps this entity to the "user" table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id // Specifies the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configures the primary key generation strategy (AUTO_INCREMENT for MySQL)
    private Long id; // Use Long for BigInt AUTO_INCREMENT

    @Column(nullable = false, unique = true, length = 50) // Maps to username column, adds constraints
    private String username;

    @Column(nullable = false, length = 100) // Maps to password column
    private String password; // Store hashed password here

    @Column(nullable = false, unique = true, length = 100) // Maps to email column
    private String email;

    @Column(length = 20) // Maps to phone column
    private String phone; // Nullable in DB, so String field is fine

    @Enumerated(EnumType.STRING) // Maps the Java Enum to a String column in DB
    @Column(name = "user_type", nullable = false) // Maps to user_type column
    private UserType userType; // Use the UserType enum

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP") // Maps to created_at
    @CreationTimestamp // Hibernate annotation for setting creation time automatically (optional, can rely on DB default)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP") // Maps to updated_at
    @UpdateTimestamp // Hibernate annotation for setting update time automatically (optional, can rely on DB default)
    private LocalDateTime updatedAt;

    // *** Add a constructor for creating new users (without ID and timestamps) ***
    public User(String username, String password, String email, String phone, UserType userType) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.userType = userType;
        // createdAt and updatedAt will be handled by DB default or JPA annotations if used
    }
}
