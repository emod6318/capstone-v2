package com.example.schdulemanagementproject.schedule.service;

import com.example.schdulemanagementproject.schedule.controller.dto.ScheduleNoIdRequest;
import com.example.schdulemanagementproject.schedule.controller.dto.ScheduleRequest;
import com.example.schdulemanagementproject.schedule.repository.entity.Schedule;

import java.util.List;

public interface ScheduleService {

    String createSchedule(ScheduleNoIdRequest request);
    List<Schedule> findSchedules(String email);
    Schedule updateSchedule(ScheduleRequest request);
    String deleteSchedule(String email,String id);
}
