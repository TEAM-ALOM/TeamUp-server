package com.kk.TeamUp.config.jwt;

import com.kk.TeamUp.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class TokenProvider {
    private final JwtProperties jwtProperties;

    //토큰 생성 메소드
    public String generateToken(User user, Duration expiredAt) {
        Date now = new Date();
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user);
    }

    private String makeToken(Date expiry, User user) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) //헤더의 "typ" : "JWT" 이것을 의미하는 것 같음
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .setSubject(user.getName())
                .claim("id", user.getId()) //claim id를 user id로 설정
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    //토큰 유효성 검사
    public boolean validToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey()) //복호화 진행 과정
                    .parseClaimsJws(token); //올바른 토큰인지 확인 진행됨
            return true;
        } catch(Exception e) {
            return false; //올바른 토큰이 아닐 시 진입 하게 됨
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }

    //토큰 기반으로 인증 정보 가져오는 메소드
    //안의 상세 코드는 아직까지는 이해를 못했음
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
        //SimpleGrantedAuthority : 계정이 갖고 있는 권한 목록 리턴해줌
        //singleton으로 단 하나의 객체만 저장 가능한 컬렉션이 됨
        //immutable 하게 되어서, 추가/삭제 등의 작업 수행 불가능하게 됨

        return new UsernamePasswordAuthenticationToken(
                new org.springframework.security.core.userdetails.User(
                        claims.getSubject(),"",authorities
                ),
                token,authorities
        );
        // 유저 객체를 만들고, 권한이 부여된 인증 토큰 발급하는 개념??
    }

    public Long getUserId(String token) {
        Claims claims = getClaims(token);
        return claims.get("id",Long.class);
    }

}
