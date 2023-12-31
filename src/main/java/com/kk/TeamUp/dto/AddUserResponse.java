package com.kk.TeamUp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddUserResponse {
    private String studentId;
    private String name;
    private String major;
    private String position;
    private String record;
    private Long penalty;
    private String schedule;


}
