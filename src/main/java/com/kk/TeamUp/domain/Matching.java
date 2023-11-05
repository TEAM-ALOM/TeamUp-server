package com.kk.TeamUp.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Matching {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",updatable = false)
    private Long id;

    @Column(name="created_user",nullable = false)
    private String createdUser;

    @Column(name="game_time")
    private LocalDateTime gameTime;

    @Column(name="category", nullable = false)
    private String category;

    @Column(name="place",nullable = false)
    private String place;

    @Column(name="title",nullable = false)
    private String title;

    @Column(name="detail", nullable = false)
    private String detail;

    @CreatedDate
    @Column(name="created_at")
    private LocalDateTime createdAt; //매칭 생성 시간

    @LastModifiedDate
    @Column(name="updated_at")
    private LocalDateTime updatedAt; //매칭 마지막 수정 시간

    @Builder
    public Matching(String createdUser, LocalDateTime gameTime,String category, String place, String title, String detail) {
        this.createdUser = createdUser;
        this.gameTime = gameTime;
        this.category =category;
        this.place =place;
        this.title = title;
        this.detail = detail;
    }


}
