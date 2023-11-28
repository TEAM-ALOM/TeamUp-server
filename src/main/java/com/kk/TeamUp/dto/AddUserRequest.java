package com.kk.TeamUp.dto;

import com.kk.TeamUp.domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddUserRequest {
    private String name;
    private String major;
    private String position;

}
