package com.kk.TeamUp.service;

import com.kk.TeamUp.config.jwt.TokenProvider;
import com.kk.TeamUp.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    public String createNewAccessToken(String refreshToken) {
        //전달받은 리프레쉬 토큰 이용하여 유효성 검사 진행
        if(!tokenProvider.validToken(refreshToken)) {
            throw new IllegalArgumentException("Unexpected token");
        }
        //리프레쉬 토큰 유효할 시, 이를 이용하여 새로운 엑세스 토큰 생성 진행
        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        User user = userService.findById(userId);

        return tokenProvider.generateToken(user, Duration.ofHours(2));
        //유효시간 이틀인 토큰 생성
        //그냥 토큰은 유효시간 짧게, 리프레쉬 토큰을 길게 설정..?
    }
}
