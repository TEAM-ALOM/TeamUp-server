package com.kk.TeamUp.config;

import com.kk.TeamUp.config.jwt.TokenProvider;
import com.kk.TeamUp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class Security {
    private final TokenProvider tokenProvider;
    private final UserService userService;

    //SecurityFilterChain이 Authentication filter의 역할을 하게 됨
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
        requestCache.setMatchingRequestParameterName(null);

        http
                .requestCache(request->request
                        .requestCache(requestCache)) //자동으로 continue 쿼리 붙여지는 것 방지하기 위한 조치
                .authorizeHttpRequests((request)->request
                        //뒤의 2개는 세종 api 테스트용 (나중에 제거 할 수 있음)
                        .requestMatchers("/api/sejong","/sejong","/error")
                        .permitAll()
                )
                .authorizeHttpRequests((request) -> request
                        .anyRequest().authenticated() //위의 특정 url 제외하고는 인증 필요함을 의미
                        //.anyRequest().permitAll()
                )
                .addFilterBefore(new TokenAuthenticationFilter(tokenProvider,userService), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable();

        return http.build();

    }

    // AuthenticationManager의 구현체 중 하나로 AuthenticationProvider가 지원됨
    //무슨 역할인지 정확히는 모르겠음 -> 굳이 직접 구현해야 하나?
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailsService userService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                //AuthenticationManagerBuilder는 AuthenticationManager를 생성하는 빌더 클래스
                .userDetailsService(userService) //사용자 정보를 로드하는데 사용됨
                .passwordEncoder(bCryptPasswordEncoder) // 암호화하여 비밀번호 정의
                .and()
                .build();
    }


    //암호화 하게 하는 메소드
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
