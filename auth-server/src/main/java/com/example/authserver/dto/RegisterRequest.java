package com.example.authserver.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    // 可以添加其他注册时需要的字段
}
