package com.example.authserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.authserver.model.UserType;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDto {
    private String userId;
    private String username;
    private String email;
    private UserType userType;
    // 可以添加其他信息，例如：roles
}
