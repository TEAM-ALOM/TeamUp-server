package com.kk.TeamUp.domain;

import jakarta.persistence.*;
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

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="matching_id")
    private Matching matching;

    public static UserMatching createUserMatching(User user, Matching matching){
        UserMatching userMatching = new UserMatching();
        userMatching.setUser(user);
        userMatching.setMatching(matching);

        return userMatching;
    }

}
