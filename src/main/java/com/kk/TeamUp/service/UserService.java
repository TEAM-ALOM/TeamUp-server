package com.kk.TeamUp.service;

import com.kk.TeamUp.domain.User;
import com.kk.TeamUp.dto.AddUserRequest;
import com.kk.TeamUp.dto.UpdateUserRequest;
import com.kk.TeamUp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    //save, update,
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User save(AddUserRequest request) {
        return userRepository.save(User.builder()
                .name(request.getName())
                .major(bCryptPasswordEncoder.encode(request.getMajor()))
                .build());
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

}
