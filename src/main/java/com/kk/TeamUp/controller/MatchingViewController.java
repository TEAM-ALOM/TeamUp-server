package com.kk.TeamUp.controller;

import com.kk.TeamUp.domain.Matching;
import com.kk.TeamUp.domain.User;
import com.kk.TeamUp.domain.UserMatching;
import com.kk.TeamUp.dto.AddMatchingRequest;
import com.kk.TeamUp.service.MatchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MatchingViewController {

    private final MatchingService matchingService;

    @GetMapping("/matching")
    public String allMatching(Model model) {
        List<Matching> matchings = matchingService.findAllMatching();
        model.addAttribute("matchings",matchings);

        List<String> userName = new ArrayList<>();
        List<UserMatching> userMatchings = new ArrayList<>();

        for(Matching matching : matchings) {
            userMatchings = matching.getUserMatchings();
            for(UserMatching userMatching : userMatchings) {
                userName.add(userMatching.getUser().getName());
            }
        }

        model.addAttribute("users",userName);
        return "/matchings";
    }

}
