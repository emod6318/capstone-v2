package com.example.schdulemanagementproject.category.repository.entitiy;

import com.example.schdulemanagementproject.user.repository.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@Setter
@Table(indexes = {@Index(name = "idx_category_name", columnList = "category_name")}) // category_name에 인덱스 추가
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 설정)
    private Integer id; // 카테고리의 번호

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user_id;


    @Column(nullable = false, name ="category_name")
    private String category_name;

    @Column(nullable = false)
    private String category_color;

}
