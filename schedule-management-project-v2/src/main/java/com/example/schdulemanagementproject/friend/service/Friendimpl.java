package com.example.schdulemanagementproject.friend.service;

import com.example.schdulemanagementproject.friend.repository.FriendRepository;
import com.example.schdulemanagementproject.friend.repository.entity.Friend;
import com.example.schdulemanagementproject.user.repository.UserRepository;
import com.example.schdulemanagementproject.user.repository.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
@Service
@RequiredArgsConstructor
public class Friendimpl implements FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    @Override
    public List<Friend> findFriends(String email) {
        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            System.out.println("User not found: " + email);
            // 사용자 미존재 시 빈 리스트 반환
            return Collections.emptyList(); // 또는 적절한 반환값
        }

        // 사용자 정보를 찾았을 경우 친구 목록 반환
        return friendRepository.findByUserEmail(user.getEmail());
    }

    @Override
    public String deleteFriend(String ownerEmail, String friendEmail) {
        List<Friend> friends = findFriends(ownerEmail);
        boolean isDeleted = false;

        for (Friend friend : friends) {
            if (friend.getUser().getEmail().equals(friendEmail)) {
                friendRepository.delete(friend);
                isDeleted = true;
                break; // 친구를 삭제한 후 반복 종료
            }
        }

        return isDeleted ? "success" : "fail"; // 삭제 여부에 따라 결과 반환
    }

    @Override
    public String createFriend(String ownerEmail, String friendEmail) {
        User ownerUser = userRepository.findByEmail(ownerEmail).orElseThrow(() -> new RuntimeException("User not found"));
        User friendUser = userRepository.findByEmail(friendEmail).orElseThrow(() -> new RuntimeException("User not found"));

        Friend friend = Friend.builder()
                .owner(ownerUser)
                .user(friendUser).build();
        friendRepository.save(friend);
        return "success";

    }

}
