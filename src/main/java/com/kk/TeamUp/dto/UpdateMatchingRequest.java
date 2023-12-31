package com.kk.TeamUp.dto;

import com.kk.TeamUp.domain.UserMatching;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class UpdateMatchingRequest {
    private LocalDateTime gameTime;
    private String place;
    private String category;
    private String title;
    private String detail;
    private List<UserMatching> userMatchings;
}
