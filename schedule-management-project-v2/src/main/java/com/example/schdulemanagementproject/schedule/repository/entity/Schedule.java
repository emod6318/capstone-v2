package com.example.schdulemanagementproject.schedule.repository.entity;

import com.example.schdulemanagementproject.category.repository.entitiy.Category;
import com.example.schdulemanagementproject.user.repository.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@Setter
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 설정)
    private Integer id; // 카테고리의 번호

    // User의 Email을 외래 키로 참조
    @ManyToOne
    @JoinColumn(name = "user_email", referencedColumnName = "email", foreignKey = @ForeignKey(name = "fk_schedule_user_email"))
    private User user_email;

    // 카테고리의 이름을 외래 키로 참조
    @ManyToOne
    @JoinColumn(name = "category_name", referencedColumnName = "category_name", foreignKey = @ForeignKey(name = "fk_schedule_category_name"))
    private Category categoryName;


    private  String schedule_name;

    @Column(nullable = false)
    private LocalDateTime start_date;
    @Column(nullable = false)
    private LocalDateTime end_date;

    private String description;
}
