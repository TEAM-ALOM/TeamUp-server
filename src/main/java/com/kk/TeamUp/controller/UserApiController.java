package com.kk.TeamUp.controller;

import com.kk.TeamUp.domain.User;
import com.kk.TeamUp.dto.AddUserRequest;
import com.kk.TeamUp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;

    @PostMapping("/api/user")
    public String UserRegister(AddUserRequest request) {
        userService.save(request);
        return "redirect:/CreateTest";
    }

    //삭제 api
    @DeleteMapping("/api/user/{id}")
    public String UserDelete(@PathVariable long id) {
        userService.delete(id);
        return "redirect:/DeleteTest";
    }
}
