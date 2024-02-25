package com.kk.TeamUp.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kk.TeamUp.config.jwt.TokenProvider;
import com.kk.TeamUp.domain.Matching;
import com.kk.TeamUp.domain.User;
import com.kk.TeamUp.domain.UserMatching;
import com.kk.TeamUp.dto.AddUserRequest;
import com.kk.TeamUp.dto.CreateAccessTokenRequest;
import com.kk.TeamUp.dto.UpdateUserRequest;
import com.kk.TeamUp.dto.VerifyUserRequest;
import com.kk.TeamUp.service.MatchingService;
import com.kk.TeamUp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final MatchingService matchingService;

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
        return "redirect:/sejong";
    }

    @PostMapping("/api/join/{userId}/{matchingId}")
    public ResponseEntity<UserMatching> joinMatching(@PathVariable Long userId, @PathVariable Long matchingId) {
        User user = userService.findById(userId);
        Matching matching = matchingService.findOneMatching(matchingId);

        UserMatching userMatching = userService.joinMatching(user,matching);

        return ResponseEntity.ok()
                .build();
    }



    // 인증 api 실험용
    // 회원이 아닐 시 회원가입 진행하고, 회원일 시, 해당 회원이 가지고 있는 리프레쉬 토큰 반환
    @PostMapping("/api/sejong")
    public ResponseEntity<Map<String,String>> sejongApi(@RequestBody VerifyUserRequest request, Model model, HttpServletResponse response) {
        try {
            String url = "https://auth.imsejong.com/auth?method=DosejongSession";

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<VerifyUserRequest> requestEntity = new HttpEntity<>(request,headers);

            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode newNode = objectMapper.readTree(responseEntity.getBody());

            User user;
            String token;
            String refreshToken;
            Map<String, String> tokenMap = new HashMap<>();

            try {
                user = userService.findByStudentId(request.getId());

                token = tokenProvider.generateToken(user, Duration.ofSeconds(1));
                refreshToken = tokenProvider.generateToken(user, Duration.ofSeconds(14));

                user = userService.updateRefreshToken(request.getId(), new CreateAccessTokenRequest(refreshToken));
                tokenProvider.setHeaderAccessToken(response, token);
                tokenProvider.setHeaderRefreshToken(response,refreshToken);

                tokenMap.put("token",token);
                tokenMap.put("refreshToken",refreshToken);
            } catch(Exception e) {
                String studentName = newNode.get("result").get("body").get("name").toString();
                String studentMajor = newNode.get("result").get("body").get("major").toString();

                user = userService.save(new AddUserRequest(request.getId(),studentName,studentMajor));

                //로그인 인증 시 토큰을 발급해줌
                //유효시간을 1시간으로 설정 -> 추후 변경 가능
                token = tokenProvider.generateToken(user, Duration.ofHours(1));
                //refresh token도 만들어서 전송
                refreshToken = tokenProvider.generateToken(user, Duration.ofDays(14));

                //리프레쉬 토큰 user 엔티티에 저장
                user = userService.updateRefreshToken(request.getId(), new CreateAccessTokenRequest(refreshToken));

                tokenMap.put("token",token);
                tokenMap.put("refreshToken",refreshToken);

                tokenProvider.setHeaderAccessToken(response, token);
                tokenProvider.setHeaderRefreshToken(response,refreshToken);
            }

            return ResponseEntity.ok()
                    .body(tokenMap);

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .build();
        }
    }

}
