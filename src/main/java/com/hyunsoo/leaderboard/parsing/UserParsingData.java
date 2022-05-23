package com.hyunsoo.leaderboard.parsing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UserParsingData implements Serializable {

    private String handle;

    private Long exp;

    private Short tier;
}
