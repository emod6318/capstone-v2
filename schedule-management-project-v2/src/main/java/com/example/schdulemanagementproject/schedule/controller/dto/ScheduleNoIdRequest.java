package com.example.schdulemanagementproject.schedule.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class ScheduleNoIdRequest {
    private String userEmail; // 사용자의 이메일
    private String categoryName; // 카테고리 이름
    private String scheduleName; // 일정 이름
    private LocalDateTime startDate; // 시작 날짜
    private LocalDateTime endDate; // 종료 날짜
    private String description; // 설명
}
