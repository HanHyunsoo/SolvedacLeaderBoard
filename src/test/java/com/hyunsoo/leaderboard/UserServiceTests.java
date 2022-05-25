package com.hyunsoo.leaderboard;

import com.hyunsoo.leaderboard.dto.UserResponse;
import com.hyunsoo.leaderboard.model.User;
import com.hyunsoo.leaderboard.model.UserRepository;
import com.hyunsoo.leaderboard.parsing.UserParsingData;
import com.hyunsoo.leaderboard.service.RedisService;
import com.hyunsoo.leaderboard.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RedisService redisService;

    private UserParsingData userParsingData() {
        UserParsingData userParsingData = new UserParsingData();
        userParsingData.setHandle("gustn8523");
        userParsingData.setExp(10000L);
        userParsingData.setTier((short) 5);

        return userParsingData;
    }

    @Test
    @DisplayName("User 저장")
    void save() {
        // given
        UserParsingData dto = userParsingData();
        User user = User.builder()
                .id(dto.getHandle())
                .exp(dto.getExp())
                .tier(dto.getTier())
                .build();

        given(userRepository.save(any())).willReturn(user);
        given(userRepository.findById(any())).willReturn(Optional.ofNullable(user));

        // when
        String userId = userService.save(dto);

        // then
        User findUser = userRepository.findById(userId).get();

        assertEquals(user.getId(), findUser.getId());
        assertEquals(user.getExp(), findUser.getExp());
        assertEquals(user.getTier(), findUser.getTier());
    }

    @Test
    @DisplayName("User 찾기")
    void findById() {
        // given
        UserParsingData dto = userParsingData();
        User user = User.builder()
                .id(dto.getHandle())
                .exp(dto.getExp())
                .tier(dto.getTier())
                .build();

        given(userRepository.findById(any())).willReturn(Optional.ofNullable(user));
        given(redisService.findRankById(any())).willReturn(1L);

        // when
        UserResponse userResponse = userService.findById(dto.getHandle());

        // then
        assertEquals(user.getId(), userResponse.getUserId());
        assertEquals(user.getExp(), userResponse.getExp());
        assertNotNull(userResponse.getTierName());
        assertNotNull(userResponse.getProfileUrl());
        assertNotNull(userResponse.getRank());
    }

    @Test
    @DisplayName("모든 User 찾기")
    void findAll() {
        // given
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            users.add(
                    User.builder()
                            .id("test" + i)
                            .exp((long) i)
                            .tier((short) i)
                            .build()
            );
        }

        given(userRepository.findAll()).willReturn(users);
        given(redisService.findRankById(any())).willReturn(1L);

        // when
        List<UserResponse> userResponses = userService.findAll();

        // then
        assertNotNull(userResponses);
    }

}
