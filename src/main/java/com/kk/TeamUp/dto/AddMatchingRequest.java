package com.kk.TeamUp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddMatchingRequest {
    private String category;
    private String place;
    private String title;
    private String detail;
}
