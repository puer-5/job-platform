package com.example.authserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String token;
    // 可以添加其他信息，例如：tokenType = "Bearer", expiresIn, refreshToken 等
}
