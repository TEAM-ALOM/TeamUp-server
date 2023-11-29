package com.kk.TeamUp.config;

import com.kk.TeamUp.config.jwt.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    //OncePerRequestFilter : http request의 한 번의 요청에 한 번만 실행하는 filter
    private final TokenProvider tokenProvider;
    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";
    // Bearer 하고 한칸 띄고 토큰이 나오기에, 빈칸 한칸을 필수로 띄워놓아야 함

    private String getAccessToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            return authorizationHeader.substring(TOKEN_PREFIX.length());
            //substring으로 인자의 인덱스부터 끝까지 잘라서 반환
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);
        //request의 헤더에 포함된 것들 중, Authorization에 해당하는 부분을 가져와 저장하게 됨

        //authorizationHeader 자체는 앞에 Bearer이라는 문구가 추가된 상태 -> 이를 제거해주어야 함
        String token = getAccessToken(authorizationHeader);

        if (tokenProvider.validToken(token)) {
            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //토큰이 유효할 시, securitycontextholder에 인증 정보를 저장하여, 다음 번 부터 접근 가능하게 설정
        }

        filterChain.doFilter(request,response);
    }


}
