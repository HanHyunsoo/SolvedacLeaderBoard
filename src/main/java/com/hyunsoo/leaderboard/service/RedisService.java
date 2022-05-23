package com.hyunsoo.leaderboard.service;

import com.hyunsoo.leaderboard.parsing.UserParsingData;
import com.hyunsoo.leaderboard.parsing.ParsingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final ParsingService parsingService;

    private final RedisTemplate<String, Object> redisTemplate;

    private final String keyName = "SKHU";

    public int saveAll(Set<UserParsingData> set) {
        ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();

        set.stream().filter(Objects::nonNull).forEach(
                x -> zSetOperations.add(keyName, x.getHandle(), x.getExp())
        );

        return set.size();
    }

    public Long findRankById(String id) {
        ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();

        return zSetOperations.rank(keyName, id);
    }

}
