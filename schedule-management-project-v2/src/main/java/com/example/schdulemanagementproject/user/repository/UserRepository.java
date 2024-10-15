package com.example.schdulemanagementproject.user.repository;

import com.example.schdulemanagementproject.user.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);
    User findByNameAndEmail(String name, String email);  // 이름과 이메일로 사용자 찾기 // 이름과 이메일로 사용자 존재 여부 확인
}
