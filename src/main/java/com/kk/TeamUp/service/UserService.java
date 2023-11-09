package com.kk.TeamUp.service;

import com.kk.TeamUp.domain.User;
import com.kk.TeamUp.dto.AddUserRequest;
import com.kk.TeamUp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    //save, update,
    private final UserRepository userRepository;

    public Long save(AddUserRequest request) {
        return userRepository.save(User.builder()
                .name(request.getName())
                .major(request.getMajor())
                .position(request.getPosition())
                .build()).getId();
    }

    public void delete(long id) {
        userRepository.deleteById(id);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not exists!"));
    }

}
