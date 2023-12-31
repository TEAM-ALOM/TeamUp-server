package com.kk.TeamUp.dto;

import com.kk.TeamUp.domain.UserMatching;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateUserMatching {
    private List<UserMatching> userMatchings;

    @Builder
    public UpdateUserMatching(List<UserMatching> userMatchings) {
        this.userMatchings = userMatchings;
    }
}
