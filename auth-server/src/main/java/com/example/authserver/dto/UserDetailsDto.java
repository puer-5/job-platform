package com.example.authserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDto {
    private String userId;
    private String username;
    private String email; // Optional, depending on whether it's in token or fetched
    // 可以添加其他信息，例如：roles
}
