package com.example.schdulemanagementproject.schedule.service;

import com.example.schdulemanagementproject.category.repository.CategoryRepository;
import com.example.schdulemanagementproject.category.repository.entitiy.Category;
import com.example.schdulemanagementproject.schedule.controller.dto.ScheduleNoIdRequest;
import com.example.schdulemanagementproject.schedule.controller.dto.ScheduleRequest;
import com.example.schdulemanagementproject.schedule.repository.ScheduleRepository;
import com.example.schdulemanagementproject.schedule.repository.entity.Schedule;
import com.example.schdulemanagementproject.user.repository.UserRepository;
import com.example.schdulemanagementproject.user.repository.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class Scheduleimpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    @Override
    public String createSchedule(ScheduleNoIdRequest request) {
        // 사용자 이메일로 User 객체 가져오기
        User user = userRepository.findByEmail(request.getUserEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 카테고리 이름으로 Category 객체 가져오기
        List<Category> categories = categoryRepository.findByUserEmail(request.getUserEmail());
        // 카테고리 찾기
        Category category = categories.stream()
                .filter(category1 -> Objects.equals(category1.getCategory_name(), request.getCategoryName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Schedule 객체 생성
        Schedule schedule = Schedule.builder()
                .user_email(user) // User 객체 설정
                .categoryName(category) // Category 객체 설정
                .schedule_name(request.getScheduleName())
                .start_date(request.getStartDate())
                .end_date(request.getEndDate())
                .description(request.getDescription())
                .build();

        // Schedule 객체를 DB에 저장
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return "success";
    }


    @Override
    public List<Schedule> findSchedules(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        List<Schedule> schedules = scheduleRepository.findByUserEmail(email);
        // 결과 출력
//        if (!schedules.isEmpty()) {
//            for (Schedule schedule : schedules) {
//                System.out.println("Schedule ID: " + schedule.getId());
//                System.out.println("Start Date: " + schedule.getStart_date());
//                System.out.println("End Date: " + schedule.getEnd_date());
//                System.out.println("Description: " + schedule.getDescription());
//                // 필요에 따라 추가 정보 출력
//                System.out.println("------------------------");
//            }
//        } else {
//            System.out.println("해당 사용자에 대한 스케줄이 존재하지 않습니다.");
//        }
        return schedules;
    }

    @Override
    public Schedule updateSchedule(ScheduleRequest request) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(request.getId());

        if (optionalSchedule.isPresent()) {
            Schedule schedule = optionalSchedule.get();
            schedule.setSchedule_name(request.getScheduleName());
            schedule.setStart_date(request.getStartDate());
            schedule.setEnd_date(request.getEndDate());
            schedule.setDescription(request.getDescription());
            schedule.setCategoryName(schedule.getCategoryName());


            return scheduleRepository.save(schedule); // user 객체를 저장하고 반환
        } else {
            throw new RuntimeException("www");
        }
    }

    @Override
    public String deleteSchedule(String email, String id) {
        // 사용자의 모든 스케줄을 가져옵니다.
        List<Schedule> schedules = scheduleRepository.findByUserEmail(email);

        // ID로 스케줄 찾기
        Schedule scheduleToDelete = null;

        for (Schedule schedule : schedules) {
            if (schedule.getId().toString().equals(id)) {
                scheduleToDelete = schedule;
                break; // 스케줄을 찾으면 루프 종료
            }
        }

        // 스케줄을 찾지 못한 경우 예외 발생
        if (scheduleToDelete == null) {
            throw new RuntimeException("Schedule not found");
        }

        // 스케줄 삭제
        scheduleRepository.delete(scheduleToDelete);
        return "success";
    }
}
