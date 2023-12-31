package com.kk.TeamUp.dto;

import com.kk.TeamUp.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AddUserRequest {
    private String studentId;
    private String name;
    private String major;

    @Builder
    public AddUserRequest(String studentId, String name, String major) {
        this.studentId = studentId;
        this.name=name;
        this.major=major;
    }

}
