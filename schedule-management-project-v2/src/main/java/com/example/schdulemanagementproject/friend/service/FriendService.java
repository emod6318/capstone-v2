package com.example.schdulemanagementproject.friend.service;

import com.example.schdulemanagementproject.friend.repository.entity.Friend;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FriendService {
    List<Friend> findFriends(String email);

    String deleteFriend(String ownerEmail,String friendEmail);

    String createFriend(String ownerEmail,String friendEmail);
}
