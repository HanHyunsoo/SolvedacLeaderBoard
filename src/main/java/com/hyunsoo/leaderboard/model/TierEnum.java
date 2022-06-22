package com.hyunsoo.leaderboard.model;

import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum TierEnum {
    UNRANK(0, "Unrank"),
    BRONZE_V(1, "Bronze V"),
    BRONZE_IV(2, "Bronze IV"),
    BRONZE_III(3, "Bronze III"),
    BRONZE_II(4, "Bronze II"),
    BRONZE_I(5, "Bronze I"),
    SILVER_V(6, "Silver V"),
    SILVER_IV(7, "Silver IV"),
    SILVER_III(8, "Silver III"),
    SILVER_II(9, "Silver II"),
    SILVER_I(10, "Silver I"),
    GOLD_V(11, "Gold V"),
    GOLD_IV(12, "Gold IV"),
    GOLD_III(13, "Gold III"),
    GOLD_II(14, "Gold II"),
    GOLD_I(15, "Gold I"),
    PLATINUM_V(16, "Platinum V"),
    PLATINUM_IV(17, "Platinum IV"),
    PLATINUM_III(18, "Platinum III"),
    PLATINUM_II(19, "Platinum II"),
    PLATINUM_I(20, "Platinum I"),
    DIAMOND_V(21, "Diamond V"),
    DIAMOND_IV(22, "Diamond IV"),
    DIAMOND_III(23, "Diamond III"),
    DIAMOND_II(24, "Diamond II"),
    DIAMOND_I(25, "Diamond I"),
    RUBY_V(26, "Ruby V"),
    RUBY_IV(27, "Ruby IV"),
    RUBY_III(28, "Ruby III"),
    RUBY_II(29, "Ruby II"),
    RUBY_I(30, "Ruby I"),
    MASTER(31, "Master");

    private final int code;
    private final String tierName;

    TierEnum(int code, String tierName) {
        this.code = code;
        this.tierName = tierName;
    }

    private static final Map<Integer, TierEnum> map =
            Collections.unmodifiableMap(
                    Stream.of(values()).collect(Collectors.toMap(TierEnum::getCode, Function.identity()))
            );

    public static TierEnum findByCode(int code) {
        return map.get(code);
    }
}
