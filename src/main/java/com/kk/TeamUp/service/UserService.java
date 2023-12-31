package com.kk.TeamUp.service;

import com.kk.TeamUp.domain.Matching;
import com.kk.TeamUp.domain.User;
import com.kk.TeamUp.domain.UserMatching;
import com.kk.TeamUp.dto.AddUserRequest;
import com.kk.TeamUp.dto.CreateAccessTokenRequest;
import com.kk.TeamUp.dto.UpdateUserRequest;
import com.kk.TeamUp.repository.UserMatchingRepository;
import com.kk.TeamUp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    //save, update,
    private final UserRepository userRepository;
    private final UserMatchingService userMatchingService;
    private final UserMatchingRepository userMatchingRepository;

    public User save(AddUserRequest request) {
        return userRepository.save(User.builder()
                .studentId(request.getStudentId())
                .name(request.getName())
                .major(request.getMajor())
                .build());
    }

    public UserMatching joinMatching(User user, Matching matching) {
        return userMatchingService.save(user,matching);
    }

    public UserMatching findMatchings(long id) {
        return userMatchingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("no matching!"));
    }

    public List<String> getMatchingTitle(List<UserMatching> userMatchings, long studentId) {
        List<String> matchingList = new ArrayList<>();
        for (UserMatching userMatching : userMatchings) {
            if (userMatching.getUser().getId() == studentId && !matchingList.contains(userMatching.getMatching().getTitle())) {
                matchingList.add(userMatching.getMatching().getTitle());
            }
        }

        return matchingList;
    }

    public void delete(long id) {
        userRepository.deleteById(id);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not exists!"));
    }

    @Transactional
    public User updateUser(long id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("not found"+ id));
        user.update(request.getName());

        return user;
    }

    @Transactional
    public User updateRefreshToken(String studentId, CreateAccessTokenRequest request) {
        User user = userRepository.findByStudentId(studentId)
                .orElseThrow(() -> new IllegalArgumentException("not found"+ studentId));
        user.setRefreshToken(request.getRefreshToken());
        return user;
    }

    public User findByStudentId(String studentId) {
        return userRepository.findByStudentId(studentId)
                .orElseThrow(() -> new IllegalArgumentException("not found" + studentId));
    }

//    @Transactional
//    public UserMatching joinMatching(User user, Matching matching) {
//        //UserMatching userMatching = UserMatching.createUserMatching(user, matching);
//
//        UserMatching userMatching = new UserMatching();
//        userMatching.setMatching(matching);
//        userMatching.setUser(user);
//
//        System.out.println(userMatching.getMatching());
//
//        //여기서 왜 진행 안되는지???!#@!#!@#
//        matching.addUserMatching(userMatching);
//        //user.addUserMatching(userMatching);
//
//        //user.updateMatching(new UpdateUserMatching(user.getUserMatchings()));
//        //matching.updateMatching(new UpdateUserMatching(matching.getUserMatchings()));
//        return userMatching;
//    }
}
