package com.kk.TeamUp.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {
    @GetMapping("/login")
    public String Login() {
        return "/login";
    }

    @GetMapping("/hihi")
    public String Hihi() {
        return "/hihi";
    }
}
