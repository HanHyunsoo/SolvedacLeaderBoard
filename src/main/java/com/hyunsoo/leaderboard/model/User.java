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

    private Long exp;

    private Short tier;

    @Builder
    public User(String id, Long exp, short tier) {
        this.id = id;
        this.exp = exp;
        this.tier = tier;
    }

    public void update(Long exp, short tier) {
        this.exp = exp;
        this.tier = tier;
    }
}
