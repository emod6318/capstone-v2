package com.example.schdulemanagementproject.user.service;

import com.example.schdulemanagementproject.user.controller.dto.LoginRequest;
import com.example.schdulemanagementproject.user.repository.UserRepository;
import com.example.schdulemanagementproject.user.repository.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceimpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public String join(String name, String email, String password) {
       User user = User.builder()
               .name(name)
               .email(email)
               .password(password).build();

       userRepository.save(user);

       return "success";
    }

    public LoginRequest login(LoginRequest loginRequest) {
         Optional<User> byUserEmail =  userRepository.findByEmail(loginRequest.getEmail());
        if(byUserEmail.isPresent()) {
            User user = byUserEmail.get();
            if(user.getPassword().equals(loginRequest.getPassword())) {
                return LoginRequest.toUserDto(user);
            }
            else{
                return null;
            }
        } else {
            return null;
        }
    }

    public String findPassword(String name, String email) {
        User user = userRepository.findByNameAndEmail(name, email);  // 사용자 조회
        if (user != null) {
            return user.getPassword();  // 비밀번호 반환
        }
        return null;
    }

    @Override
    public Map<String,String> myInfo(String email) {

        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()) {
            Map<String,String> response = new HashMap<>();
            response.put("email",user.get().getEmail());
            response.put("name",user.get().getName());
            return response;
        }
        return null;

    }

    @Override
    public String deleteUser(String email)
    {
        Optional<User> user = userRepository.findByEmail(email);

        if(user.isPresent()) {
            User realUser = userRepository.findByNameAndEmail(user.get().getName(),user.get().getEmail());
            userRepository.delete(realUser);
        }
        return "success";
    }

    @Override
    public User updateUser(String email, User userDetails) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setName(userDetails.getName());
            // 비밀번호는 보안을 위해 해싱하는 것이 좋음.
            user.setPassword(userDetails.getPassword()); // 비밀번호 해싱 필요

            return userRepository.save(user); // user 객체를 저장하고 반환
        } else {
            throw new RuntimeException("www");
        }
    }
}
