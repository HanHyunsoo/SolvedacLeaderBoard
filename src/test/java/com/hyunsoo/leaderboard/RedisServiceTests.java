package com.hyunsoo.leaderboard;

import com.hyunsoo.leaderboard.parsing.UserParsingData;
import com.hyunsoo.leaderboard.service.RedisService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
public class RedisServiceTests {

    @InjectMocks
    private RedisService redisService;

    @Mock
    private ZSetOperations<String, Object> zSetOperations;

    private Set<UserParsingData> userParsingDataSet() {
        Set<UserParsingData> set = new HashSet<>();

        for (int i = 0; i < 10000; i++) {
            UserParsingData userParsingData = new UserParsingData();
            userParsingData.setHandle("gustn" + i);
            userParsingData.setExp((long) (Math.random() * 10000));
            userParsingData.setTier((short) (Math.random() * 30 + 1));
            set.add(userParsingData);
        }

        return set;
    }


    @Test
    @DisplayName("User 정보들을 Redis에 저장")
    void saveAll() {
        // given
        Set<UserParsingData> set = userParsingDataSet();

        // when
        int size = redisService.saveAll(set);

        // then
        assertEquals(set.size(), size);
    }

    @Test
    @DisplayName("해당 UserId의 rank 반환 테스트")
    void findRankByUserId() {
        // given
        String userId = "testId";
        Long fakeRank = 1L;
        given(zSetOperations.rank(any(), any())).willReturn(1L);

        // when
        Long rankNumber = redisService.findRankById(userId);

        // then
        assertEquals(fakeRank, rankNumber);
    }
}
