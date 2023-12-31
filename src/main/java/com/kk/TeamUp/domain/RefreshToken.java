package com.kk.TeamUp.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="refreshToken_id",updatable = false)
    private Long id;

    @Column(name="user_id",nullable = false,unique = true)
    private Long userId;

    @Column(name="refresh_token",nullable = false)
    private String refreshToken;

    //

    public RefreshToken(Long userId, String refreshToken) {
        this.userId = userId;
        this.refreshToken =refreshToken;
    }

    // 리프레쉬 토큰을 업데이트 후, 업데이트한 객체 리턴
    public RefreshToken update(String newRefreshToken) {
        this.refreshToken = newRefreshToken;
        return this;
    }
}
