package com.hyunsoo.leaderboard.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    private String id;

    private Long rating;

    private Short tier;

    @Builder
    public User(String id, Long rating, short tier) {
        this.id = id;
        this.rating = rating;
        this.tier = tier;
    }

    public void update(Long rating, short tier) {
        this.rating = rating;
        this.tier = tier;
    }
}
