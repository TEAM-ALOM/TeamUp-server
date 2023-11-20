package com.kk.TeamUp.dto;

import com.kk.TeamUp.domain.Matching;
import jakarta.persistence.Column;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AddMatchingResponse {

    private LocalDateTime gameTime;
    private String category;
    private String place;
    private String title;
    private String detail;

    public AddMatchingResponse(Matching matching) {
        this.gameTime = matching.getGameTime();
        this.category = matching.getCategory();
        this.place = matching.getPlace();
        this.title = matching.getTitle();
        this.detail = matching.getDetail();
    }
}
