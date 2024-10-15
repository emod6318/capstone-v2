package com.example.schdulemanagementproject.user.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;


@Entity
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@Setter
@Table(indexes = {@Index(name = "idx_user_email", columnList = "email")}) // 이메일에 인덱스 추가
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 설정)
    private Integer id; // UUID로 사용할 String 타입의 id 필드
    @Column(nullable = false)  // email 컬럼에 대해 유니크 설정
    private String name;
    @Column(unique = true,nullable = false)  // email 컬럼에 대해 유니크 설정
    private String email;
    @Column(nullable = false)  // email 컬럼에 대해 유니크 설정
    private String password;

}