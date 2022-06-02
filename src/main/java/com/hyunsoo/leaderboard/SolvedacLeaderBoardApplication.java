package com.hyunsoo.leaderboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SolvedacLeaderBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(SolvedacLeaderBoardApplication.class, args);
    }

}
