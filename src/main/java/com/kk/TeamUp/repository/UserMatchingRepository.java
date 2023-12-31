package com.kk.TeamUp.repository;

import com.kk.TeamUp.domain.UserMatching;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMatchingRepository extends JpaRepository<UserMatching, Long> {
}
