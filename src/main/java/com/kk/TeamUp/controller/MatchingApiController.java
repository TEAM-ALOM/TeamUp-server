package com.kk.TeamUp.controller;

import com.kk.TeamUp.domain.Matching;
import com.kk.TeamUp.dto.AddMatchingRequest;
import com.kk.TeamUp.dto.UpdateMatchingRequest;
import com.kk.TeamUp.service.MatchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MatchingApiController {

    private final MatchingService matchingService;

    @PostMapping("/api/matching")
    public ResponseEntity<Matching> makeMatching(@RequestBody AddMatchingRequest request) {
        Matching matching = matchingService.saveMatching(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(matching);
    }

    @DeleteMapping("/api/matching/{id}")
    public ResponseEntity<Void> deleteMatching(@PathVariable long id) {
        matchingService.deleteMatching(id);
        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/api/matching/{id}")
    public ResponseEntity<Matching> updateMatching(@PathVariable long id, @RequestBody UpdateMatchingRequest request) {
        Matching matching = matchingService.updateMatching(id, request);
        return ResponseEntity.ok()
                .body(matching);
    }
}
