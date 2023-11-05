package com.kk.TeamUp.repository;

import com.kk.TeamUp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
