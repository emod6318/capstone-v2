package com.example.schdulemanagementproject.friend.controller;

import com.example.schdulemanagementproject.friend.repository.entity.Friend;
import com.example.schdulemanagementproject.friend.service.FriendService;
import com.example.schdulemanagementproject.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;
    private final UserRepository userRepository;

    @GetMapping("/friends")
    public ResponseEntity<List<Map<String, String>>> getFriends(HttpSession session) {
        String email = session.getAttribute("loginEmail").toString();
        List<Friend> friends = friendService.findFriends(email);
        List<Map<String,String>> Friends = new ArrayList<>();
        for (int i = 0; i < friends.size(); i++) {
            String owner_email = friends.get(i).getOwner().getEmail();
            String user_email = friends.get(i).getUser().getEmail();
            String user_name = friends.get(i).getUser().getName();

            Map<String, String> addCategory = new HashMap<>();
            addCategory.put("owner_email", owner_email);
            addCategory.put("user_email", user_email);
            addCategory.put("user_name", user_name);


            Friends.add(addCategory);
        }
        return ResponseEntity.ok(Friends);
    }

    @DeleteMapping(value = "/delete/friend/{email}/{friendEmail}")
    public ResponseEntity<String> deleteFriend(@PathVariable String email,@PathVariable String friendEmail) {
        String result = friendService.deleteFriend(email,friendEmail);
        if(result.equalsIgnoreCase("success")) {
            return ResponseEntity.ok("User deleted successfully"); // 200 OK 응답
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found"); // 404 Not Found
        }
    }

    @PostMapping("/friend/create")
    public ResponseEntity<String> createFriend(@RequestBody Map<String,String> request)
    {
        String myEmail = request.get("myEmail");
        String friendEmail = request.get("friendEmail");
        if(userRepository.findByEmail(friendEmail).isPresent()){
            String result = friendService.createFriend(myEmail,friendEmail);
            if(result.equalsIgnoreCase("success")) {
                return ResponseEntity.ok("User deleted successfully"); // 200 OK 응답
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found"); // 404 Not Found
            }
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found"); // 404 Not Found
        }

    }
}
