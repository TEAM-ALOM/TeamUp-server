package com.kk.TeamUp.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kk.TeamUp.domain.User;
import com.kk.TeamUp.dto.AddUserRequest;
import com.kk.TeamUp.dto.UpdateUserRequest;
import com.kk.TeamUp.dto.VerifyUserRequest;
import com.kk.TeamUp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;

    @PostMapping("/api/user")
    public ResponseEntity<User> userRegister(AddUserRequest request) {
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

    // 로그아웃 기능 api
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication!=null) {
            new SecurityContextLogoutHandler().logout(request, response,authentication);
        }
        return "redirect:/login";
    }

    // 인증 api 실험용
    @PostMapping("/api/sejong")
    public ResponseEntity<JsonNode> sejongApi(VerifyUserRequest request, Model model) {
        try {
            String url = "https://auth.imsejong.com/auth?method=DosejongSession";

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<VerifyUserRequest> requestEntity = new HttpEntity<>(request,headers);

            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode newNode = objectMapper.readTree(responseEntity.getBody());


            return ResponseEntity.ok()
                    .body(newNode);

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .build();
        }
    }

}
