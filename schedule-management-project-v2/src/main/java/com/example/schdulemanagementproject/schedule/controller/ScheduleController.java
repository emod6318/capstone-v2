package com.example.schdulemanagementproject.schedule.controller;

import com.example.schdulemanagementproject.category.repository.entitiy.Category;
import com.example.schdulemanagementproject.schedule.controller.dto.ScheduleNoEmailRequest;
import com.example.schdulemanagementproject.schedule.controller.dto.ScheduleNoIdRequest;
import com.example.schdulemanagementproject.schedule.controller.dto.ScheduleRequest;
import com.example.schdulemanagementproject.schedule.repository.entity.Schedule;
import com.example.schdulemanagementproject.schedule.service.ScheduleService;
import com.example.schdulemanagementproject.user.repository.UserRepository;
import com.example.schdulemanagementproject.user.repository.entity.User;
import com.example.schdulemanagementproject.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/schedules/create")
    public String createSchedule(@RequestBody ScheduleNoIdRequest request, HttpSession session) {
        request.setUserEmail(session.getAttribute("loginEmail").toString());
        String result = scheduleService.createSchedule(request);
        if (Objects.equals(result, "success")) {
            System.out.println(result);
            return "redirect:/home";
        }
        return null;
    }

    @GetMapping("/schedules/get")
    public ResponseEntity<List<Map<String, String>>> getSchedules(HttpSession session) {
        String email = session.getAttribute("loginEmail").toString();
        List<Schedule> schedules = scheduleService.findSchedules(email);
        List<Map<String,String>> Schedules = new ArrayList<>();
        for (int i = 0; i < schedules.size(); i++) {
            String category_name = schedules.get(i).getCategoryName().getCategory_name();
            Map<String, String> addSchedules = getSchedules(schedules, i, category_name);

            Schedules.add(addSchedules);
        }
        return ResponseEntity.ok(Schedules);
    }
    @PostMapping("/schedules/get")
    public ResponseEntity<List<Map<String, String>>> getFriendSchedules(@RequestBody Map<String, String> requestBody,HttpSession session) {
        List<Schedule> schedules = scheduleService.findSchedules(requestBody.get("email"));
        List<Map<String,String>> Schedules = new ArrayList<>();
        for (int i = 0; i < schedules.size(); i++) {
            String category_name = schedules.get(i).getCategoryName().getCategory_name();
            Map<String, String> addSchedules = getSchedules(schedules, i, category_name);

            Schedules.add(addSchedules);
        }
        return ResponseEntity.ok(Schedules);
    }
    @DeleteMapping(value = "/delete/schedule/{email}/{id}")
    public ResponseEntity<String> deleteFriend(@PathVariable String email,@PathVariable String id) {
        String result = scheduleService.deleteSchedule(email, id);
        if(result.equalsIgnoreCase("success")) {
            return ResponseEntity.ok("User deleted successfully"); // 200 OK 응답
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found"); // 404 Not Found
        }
    }

    @PutMapping("/update/schedule")
    public ResponseEntity<Schedule> updateUser(@RequestBody ScheduleRequest request,HttpSession session)
    {
        return ResponseEntity.ok(scheduleService.updateSchedule(request));
    }

    private static Map<String, String> getSchedules(List<Schedule> schedules, int i, String category_name) {
        Integer scheduleId = schedules.get(i).getId();
        String category_color = schedules.get(i).getCategoryName().getCategory_color();
        String description = schedules.get(i).getDescription();
        String end_date = schedules.get(i).getEnd_date().toString();
        String start_date = schedules.get(i).getStart_date().toString();
        String user_email = schedules.get(i).getUser_email().getEmail();
        String schedule_name = schedules.get(i).getSchedule_name();
        Map<String, String> addSchedules = new HashMap<>();

        addSchedules.put("category_name", category_name);
        addSchedules.put("category_color", category_color);
        addSchedules.put("description", description);
        addSchedules.put("end_date", end_date);
        addSchedules.put("start_date", start_date);
        addSchedules.put("user_email", user_email);
        addSchedules.put("schedule_name", schedule_name);
        addSchedules.put("schedule_id", scheduleId.toString());

        return addSchedules;
    }

}
