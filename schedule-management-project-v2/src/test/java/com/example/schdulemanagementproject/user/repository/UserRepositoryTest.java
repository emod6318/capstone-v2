package com.example.schdulemanagementproject.user.repository;


import com.example.schdulemanagementproject.user.repository.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

@SpringBootTest
@RunWith( SpringJUnit4ClassRunner.class )
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test //create test
    public void crudTest(){
       User user = User.builder()
               .name("유저이름")
               .email("email@gmail.com")
               .build();

        // Create test
        User savedUser = userRepository.save(user); // 사용자 저장

        // Get test
        User foundUser = userRepository.findById(savedUser.getId().toString()).orElse(null); // 저장된 ID로 검색

        // 결과 확인
        assertNotNull(foundUser); // foundUser가 null이 아님을 확인
    }

 }
