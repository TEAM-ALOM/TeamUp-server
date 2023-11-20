package com.kk.TeamUp.controller;

import com.kk.TeamUp.domain.Matching;
import com.kk.TeamUp.domain.User;
import com.kk.TeamUp.domain.UserMatching;
import com.kk.TeamUp.repository.MatchingRepository;
import com.kk.TeamUp.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserViewControllerTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MatchingRepository matchingRepository;

    @Test
    void idontknow() {

        User user = userRepository.findById(1L).get();
        Matching matching = matchingRepository.findById(1L).get();

        UserMatching userMatching = UserMatching.createUserMatching(user,matching);

        System.out.println(userMatching.getUser().getName());
        System.out.println(userMatching.getMatching().getCategory());
    }
}