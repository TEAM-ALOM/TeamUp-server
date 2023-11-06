package com.kk.TeamUp.controller;

import com.kk.TeamUp.dto.AddUserRequest;
import com.kk.TeamUp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;

    @PostMapping("/login")
    public String UserRegister(AddUserRequest request) {
        userService.save(request);
        return "redirect:/hihi";
    }
}
