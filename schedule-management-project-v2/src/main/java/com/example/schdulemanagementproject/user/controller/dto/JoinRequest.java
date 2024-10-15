package com.example.schdulemanagementproject.user.controller.dto;

import lombok.Data;

@Data
public class JoinRequest {
    private String name;
    private String email;
    private String password;
}
