package com.example.schdulemanagementproject.user.service;

import com.example.schdulemanagementproject.user.controller.dto.LoginRequest;
import com.example.schdulemanagementproject.user.repository.entity.User;

import java.util.Map;

public interface UserService {

    String join(String name, String email, String password);


    LoginRequest login(LoginRequest loginRequest);

    String findPassword(String name, String email);

    Map<String,String> myInfo(String email);

    String deleteUser(String email);

    User updateUser(String email, User userDetail);
}
