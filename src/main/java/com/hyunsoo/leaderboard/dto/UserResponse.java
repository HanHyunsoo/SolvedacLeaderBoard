package com.hyunsoo.leaderboard.dto;

import com.hyunsoo.leaderboard.model.TierEnum;
import com.hyunsoo.leaderboard.model.User;
import lombok.Getter;

@Getter
public class UserResponse {

    private final String userId;

    private final Long rating;

    private final String tierName;

    private final String profileUrl;

    private final Long rank;

    public UserResponse(User entity, Long rank) {
        this.userId = entity.getId();
        this.rating = entity.getRating();
        this.tierName = TierEnum.findByCode(entity.getTier()).getTierName();
        this.profileUrl = "https://solved.ac/profile/" + userId;
        this.rank = rank;
    }
}
