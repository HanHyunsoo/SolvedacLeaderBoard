package com.hyunsoo.leaderboard.service;

import com.hyunsoo.leaderboard.parsing.UserParsingData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final ZSetOperations<String, Object> zSetOperations;

    private final String keyName = "SKHU";

    /**
     * @param set User 파싱한 정보 set
     * @return set size 반환
     * UserParsingData 들을 redis에 sorted set형태로 저장
     */
    public int saveAll(Set<UserParsingData> set) {
        set.stream().filter(Objects::nonNull).forEach(
                x -> zSetOperations.add(keyName, x.getHandle(), x.getRating())
        );

        return set.size();
    }

    /**
     * @param id user id
     * @return rank number
     * 해당 user의 rank를 반환
     */
    public Long findRankById(String id) {
        return zSetOperations.reverseRank(keyName, id);
    }

}
