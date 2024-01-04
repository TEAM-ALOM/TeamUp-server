package com.kk.TeamUp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserMatching {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_matching_id", updatable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="matching_id")
    private Matching matching;

    @Builder
    public UserMatching(User user, Matching matching) {
        this.user = user;
        this.matching=matching;
    }


}
