package com.example.schdulemanagementproject.friend.repository;


import com.example.schdulemanagementproject.category.repository.entitiy.Category;
import com.example.schdulemanagementproject.friend.repository.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface FriendRepository extends JpaRepository<Friend, Integer> {
    @Query("SELECT s FROM Friend s WHERE s.owner.email = :email")
    List<Friend> findByUserEmail(@Param("email") String email);
}
