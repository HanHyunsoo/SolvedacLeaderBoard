package com.hyunsoo.leaderboard.scheduler;

import com.hyunsoo.leaderboard.parsing.ParsingService;
import com.hyunsoo.leaderboard.parsing.UserParsingData;
import com.hyunsoo.leaderboard.service.RedisService;
import com.hyunsoo.leaderboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
@EnableAsync
@RequiredArgsConstructor
public class Scheduler {

    private final ParsingService parsingService;

    private final RedisService redisService;

    private final UserService userService;

    // 매주 월요일 02시 시작
    @Scheduled(cron = "0 0 2 * * MON")
    public void parsingAndSave() throws IOException {
        Set<UserParsingData> set = parsingService.getAllUserInfo();

        redisService.saveAll(set);
        userService.saveAll(set);
    }
}
