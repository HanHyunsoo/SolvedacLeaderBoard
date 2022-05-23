package com.hyunsoo.leaderboard.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    private String id;

    private Long exp;

    // TODO: 2022/05/23 tier 번호를 이용해서 티어 이름으로 바꿔야함
    // ex) 1 -> Bronze V
//    private String tierName;

    @Builder
    public User(String id, Long exp) {
        this.id = id;
        this.exp = exp;
    }
}
