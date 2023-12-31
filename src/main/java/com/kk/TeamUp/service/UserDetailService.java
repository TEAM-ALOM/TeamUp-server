package com.kk.TeamUp.service;

import com.kk.TeamUp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;


    // 사용자 정보를 가져올 때, 학번으로 찾아 가져올 수 있도록 해주는 서비스 로직
    @Override
    public UserDetails loadUserByUsername(String studentId)  {
        return userRepository.findByStudentId(studentId)
                .orElseThrow(() -> new IllegalArgumentException((studentId)));
    }
}
