package com.example.schdulemanagementproject.home.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    @GetMapping("/")
    public String loginForm(HttpSession session) {
        // 세션에 로그인 정보가 있으면 메인 페이지로 리다이렉트
        session.removeAttribute("loginEmail");
        if (session.getAttribute("loginEmail") != null) {
            return "redirect:/home"; // 로그인 상태에서 메인 페이지로 리다이렉트
        }
        return "index"; // 로그인 페이지로 이동
    }

    @GetMapping("/home")
    public String main(){ return "home";}


    @GetMapping("/signup")
    public String signup(){ return "signup";}

    @GetMapping("/findpw")
    public String findpw(){ return "findpw";}

    @GetMapping("/my-page")
    public String goMyPage() {
        return  "my-page";
    }

    @GetMapping("/schedule")
    public String goSchedule() {
        return  "schedule";
    }

    @GetMapping("/category")
    public String goCategory() {
        return  "category";
    }

    @GetMapping("/calender")
    public String getCalender()
    {
        return  "calender";
    }
}
