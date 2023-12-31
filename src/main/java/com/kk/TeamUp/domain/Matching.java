package com.kk.TeamUp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kk.TeamUp.dto.UpdateUserMatching;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor()
public class Matching {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="matching_id")
    private Long id;

    @OneToMany(mappedBy = "matching", cascade = CascadeType.ALL)
    private List<UserMatching> userMatchings = new ArrayList<UserMatching>();

    @Column(name="game_time")
    private LocalDateTime gameTime;

    @Column(name="category", nullable = false)
    private String category;

    @Column(name="place",nullable = false)
    private String place;

    @Column(name="title",nullable = false)
    private String title;

    @Column(name="detail",nullable = false)
    private String detail;

    @CreatedDate
    @Column(name="created_at")
    private LocalDateTime createdAt; //매칭 생성 시간

    @LastModifiedDate
    @Column(name="updated_at")
    private LocalDateTime updatedAt; //매칭 마지막 수정 시간


    public void addUserMatching(UserMatching userMatching) {
        //System.out.println(userMatching.getMatching());
        userMatchings.add(userMatching);
        //userMatching.setMatching(this);
    }

    @Builder
    public Matching(LocalDateTime gameTime,String category, String place, String title, String detail) {
        this.gameTime = gameTime;
        this.category =category;
        this.place =place;
        this.title = title;
        this.detail = detail;
    }

    public void updateMatching(UpdateUserMatching request) {
        this.userMatchings = userMatchings;
    }
}
