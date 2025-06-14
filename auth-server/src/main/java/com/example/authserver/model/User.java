package com.example.authserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Lombok: Generates getters, setters, toString, equals, and hashCode
@NoArgsConstructor // Lombok: Generates a no-argument constructor
@AllArgsConstructor // Lombok: Generates a constructor with all fields
public class User {
    private String id; // Simple ID for in-memory storage
    private String username;
    private String passwordHash; // Storing hashed password
    private String email;
    // 可以根据需要添加其他字段，例如：roles, status, createTime 等
}

