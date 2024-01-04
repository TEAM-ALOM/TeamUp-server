package com.kk.TeamUp.service;

import com.kk.TeamUp.domain.Matching;
import com.kk.TeamUp.domain.User;
import com.kk.TeamUp.domain.UserMatching;
import com.kk.TeamUp.repository.UserMatchingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserMatchingService {
    private final UserMatchingRepository userMatchingRepository;

    public UserMatching save(User user, Matching matching) {
        UserMatching userMatching = UserMatching.builder()
                        .user(user)
                        .matching(matching)
                        .build();

        userMatchingRepository.save(userMatching); //여기가 문제

        return userMatching;
    }

    public List<UserMatching> findAllUserMatchings() {
        return userMatchingRepository.findAll();
    }
}
