package com.example.schdulemanagementproject.schedule.repository;

import com.example.schdulemanagementproject.schedule.repository.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Component
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    @Query("SELECT s FROM Schedule s WHERE s.user_email.email = :email")
    List<Schedule> findByUserEmail(@Param("email") String email);
}
