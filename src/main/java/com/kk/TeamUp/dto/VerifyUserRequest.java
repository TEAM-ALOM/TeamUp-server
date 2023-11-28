package com.kk.TeamUp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyUserRequest {
    private String id;
    private String pw;
}
