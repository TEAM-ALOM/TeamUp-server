package com.kk.TeamUp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateUserRequest {
    private final String name;
    private final String major;
    private final String position;
    private final String record;
    private final Long penalty;
}
