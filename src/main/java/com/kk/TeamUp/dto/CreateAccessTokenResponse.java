package com.kk.TeamUp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateAccessTokenResponse {
    private String accessToken;
    //요청은 리프레쉬 토큰으로 받고, response는 이를 통해 생성된 새로운 access Token을 보내기 때문에
}
