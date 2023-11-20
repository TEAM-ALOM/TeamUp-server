package com.kk.TeamUp.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UpdateMatchingRequest {
    private LocalDateTime gameTime;
    private String place;
    private String category;
    private String title;
    private String detail;

}
