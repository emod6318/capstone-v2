package com.example.schdulemanagementproject.user.controller;




import com.example.schdulemanagementproject.category.repository.entitiy.dto.CategoryRequest;
import com.example.schdulemanagementproject.category.service.CategoryService;
import com.example.schdulemanagementproject.user.controller.dto.LoginRequest;
import com.example.schdulemanagementproject.user.repository.entity.User;
import com.example.schdulemanagementproject.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final CategoryService categoryService;
    // 회원가입
    @PostMapping("/join")
    public String join(@RequestParam String name, @RequestParam String email, @RequestParam String password) {
        String result = userService.join(name, email, password);
        CategoryRequest category = new CategoryRequest();
        category.setColor("#F781D8");
        category.setName("미등록");
        if(result.equalsIgnoreCase("success")){
            String resultTo = categoryService.createCategory(category,email);
            if(resultTo.equals("success")){
                return "redirect:/";  // 회원가입 성공 시 index 페이지로 리다이렉트
            }
            return "redirect:/login";
        } else {
            return "redirect:/join";  // 실패 시 다시 회원가입 페이지로 리다이렉트
        }
    }
    @PostMapping("/index")
    public String loginPro(@RequestParam String email, @RequestParam String password, HttpSession session) {
        LoginRequest loginRequest = new LoginRequest();// LoginRequest 객체 생성
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);
        LoginRequest loginResult = userService.login(loginRequest);
        if(loginResult != null) {
            //성공
            session.setAttribute("loginEmail", loginResult.getEmail()); // 세션에 이메일 저장 (또는 필요한 정보)
            String loginEmail = (String) session.getAttribute("loginEmail");
            System.out.println(loginEmail);
            return "redirect:/home"; // 리다이렉트
        }else{
            //실패
            return null;
        }

    }

    @PostMapping("/check")
    public ResponseEntity<String> checkInfo(@RequestBody Map<String, String> request, HttpSession session) {
        LoginRequest loginRequest = new LoginRequest();// LoginRequest 객체 생성
        loginRequest.setEmail(request.get("email"));
        loginRequest.setPassword(request.get("password"));
        LoginRequest loginResult = userService.login(loginRequest);
        if(loginResult != null) {
            //성공
            return ResponseEntity.ok("success"); // 리다이렉트
        }else{
            //실패
            System.out.println("wwwww");
            return null;
        }

    }
    @PostMapping("/find-password")
    public ResponseEntity<Map<String, Object>> findPassword(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        String email = request.get("email");

        String password = userService.findPassword(name, email);  // 비밀번호 찾기 로직

        Map<String, Object> response = new HashMap<>();
        if (password != null) {
            response.put("found", true);
            response.put("password", password);  // 비밀번호 클라이언트로 반환
        } else {
            response.put("found", false);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/my-info")
    public ResponseEntity<Map<String,String>> myInfo(HttpSession session)
    {
        Map<String,String> response =  userService.myInfo((String) session.getAttribute("loginEmail"));;
        return ResponseEntity.ok(response);
    }
    @DeleteMapping(value = "/delete/user/{email}")
    public String deleteUser(@PathVariable String email) {
        String result = userService.deleteUser(email);
        if(result.equalsIgnoreCase("success")) {
            return "/index"; // 200 OK 응답
        } else {
            return "/my-page"; // 404 Not Found
        }
    }

    @PutMapping("/user/update")
    public ResponseEntity<User> updateUser(@RequestBody Map<String, String> request,HttpSession session)
    {
        String name = request.get("name");
        String pw = request.get("password");
        User userDetails = User.builder().name(name).password(pw).build();

        User updatedUser = userService.updateUser(session.getAttribute("loginEmail").toString(), userDetails);
        return ResponseEntity.ok(updatedUser);
    }


}
