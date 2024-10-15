package com.example.schdulemanagementproject.friend.repository.entity;

import com.example.schdulemanagementproject.category.repository.entitiy.Category;
import com.example.schdulemanagementproject.user.repository.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 설정)
    private Integer id; // 친구의 번호

    // User의 name을 외래 키로 참조
    @ManyToOne
    @JoinColumn(name = "owner_email", referencedColumnName = "email")
    private User owner;
    // User의 name을 외래 키로 참조
    @ManyToOne
    @JoinColumn(name = "user_email",referencedColumnName = "email")
    private User user;

}
