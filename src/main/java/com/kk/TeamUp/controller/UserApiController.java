package com.kk.TeamUp.controller;

import com.kk.TeamUp.domain.User;
import com.kk.TeamUp.dto.AddUserRequest;
import com.kk.TeamUp.dto.UpdateUserRequest;
import com.kk.TeamUp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;

    @PostMapping("/api/user")
    public ResponseEntity<User> userRegister(@RequestBody AddUserRequest request) {
        User user = userService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(user);
    }

    //삭제 api
    @DeleteMapping("/api/user/{id}")
    public ResponseEntity<Void> userDelete(@PathVariable long id) {
        userService.delete(id);
        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/api/user/{id}")
    public ResponseEntity<User> userUpdate(@PathVariable long id, @RequestBody UpdateUserRequest request) {
        User user = userService.updateUser(id, request);
        return ResponseEntity.ok()
                .body(user);
    }

}
