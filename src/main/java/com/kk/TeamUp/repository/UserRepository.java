package com.kk.TeamUp.repository;

import com.kk.TeamUp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);
    Optional<User> findByStudentId(String studentId);
}
