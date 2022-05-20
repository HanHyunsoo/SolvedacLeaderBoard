package com.hyunsoo.leaderboard.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UserResponse implements Serializable {

    private String handle;

    private Long exp;
}
