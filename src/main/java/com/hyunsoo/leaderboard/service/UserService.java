package com.hyunsoo.leaderboard.service;

import com.hyunsoo.leaderboard.dto.UserResponse;
import com.hyunsoo.leaderboard.model.User;
import com.hyunsoo.leaderboard.model.UserRepository;
import com.hyunsoo.leaderboard.parsing.UserParsingData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final RedisService redisService;

    /**
     * @param data user 정보
     * @return user pk
     * user 정보를 database에 저장
     */
    @Transactional
    public String save(UserParsingData data) {
        if (userRepository.existsById(data.getHandle())) {
            User user = userRepository.getById(data.getHandle());
            user.update(data.getRating(), data.getTier());
        }

        User user = User.builder()
                .id(data.getHandle())
                .rating(data.getRating())
                .build();

        userRepository.save(user);
        return user.getId();
    }

    /**
     * @param id user id
     * @return User Response
     * 해당 user의 정보를 반환
     */
    @Transactional(readOnly = true)
    public UserResponse findById(String id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User를 찾을 수 없습니다.")
        );

        return new UserResponse(user, redisService.findRankById(id));
    }

    /**
     * @return User Response List
     * UserResponse 들 반환
     */
    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        List<User> users = userRepository.findAll();
        PriorityQueue<UserResponse> queue = new PriorityQueue<>(
                (UserResponse o1, UserResponse o2) -> o1.getRank() >= o2.getRank() ? 1 : -1
                );

        users.forEach(x -> queue.add(new UserResponse(x, redisService.findRankById(x.getId()))));

        return new ArrayList<>(queue);
    }
}
