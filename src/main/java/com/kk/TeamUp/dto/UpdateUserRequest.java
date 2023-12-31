package com.kk.TeamUp.dto;

import com.kk.TeamUp.domain.UserMatching;
import lombok.*;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class UpdateUserRequest {
    private final String name;
    private final List<UserMatching> userMatchings;

}
