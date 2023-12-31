package com.kk.TeamUp.controller;

import com.kk.TeamUp.domain.Matching;
import com.kk.TeamUp.domain.User;
import com.kk.TeamUp.domain.UserMatching;
import com.kk.TeamUp.dto.AddMatchingRequest;
import com.kk.TeamUp.dto.UpdateMatchingRequest;
import com.kk.TeamUp.service.MatchingService;
import com.kk.TeamUp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MatchingApiController {

    private final MatchingService matchingService;
    private final UserService userService;

    @PostMapping("/api/matching")
    public ResponseEntity<Matching> makeMatching(@RequestBody AddMatchingRequest request) {
        User user = userService.findByStudentId("21011859");
        Matching matching = matchingService.saveMatching(user, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(matching);
    }

    @GetMapping("/test")
    public List<UserMatching> test() {
        Matching matching = matchingService.findOneMatching(1L);
        return matching.getUserMatchings();
    }

    @DeleteMapping("/api/matching/{id}")
    public ResponseEntity<Void> deleteMatching(@PathVariable long id) {
        matchingService.deleteMatching(id);
        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/api/matching/{id}")
    public ResponseEntity<Matching> updateMatching(@PathVariable long id, UpdateMatchingRequest request) {
        Matching matching = matchingService.updateMatching(id, request);
        return ResponseEntity.ok()
                .body(matching);
    }
}
