package com.example.schdulemanagementproject.user.controller.dto;

import com.example.schdulemanagementproject.user.repository.entity.User;
import lombok.Data;

@Data
public class LoginRequest {
    private String email;    // 로그인 시 이메일
    private String password; // 로그인 시 비밀번호

    public static LoginRequest toUserDto(User user) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(user.getEmail());
        loginRequest.setPassword(user.getPassword());
        return loginRequest;
    }
}
