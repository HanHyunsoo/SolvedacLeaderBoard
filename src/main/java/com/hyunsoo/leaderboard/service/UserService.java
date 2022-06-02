package com.hyunsoo.leaderboard.service;

import com.hyunsoo.leaderboard.dto.UserResponse;
import com.hyunsoo.leaderboard.model.User;
import com.hyunsoo.leaderboard.model.UserRepository;
import com.hyunsoo.leaderboard.parsing.UserParsingData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
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
        User user;

        if (userRepository.existsById(data.getHandle())) {
            user = userRepository.getById(data.getHandle());
            user.update(data.getRating(), data.getTier());
        } else {
            user = User.builder()
                    .id(data.getHandle())
                    .rating(data.getRating())
                    .tier(data.getTier())
                    .build();
        }

        userRepository.save(user);
        return user.getId();
    }

    /**
     * @param set User info들
     * @return 저장한 엔티티 개수
     *
     */
    @Transactional
    public int saveAll(Set<UserParsingData> set) {
        List<User> users = set.stream()
                .filter(Objects::nonNull)
                .map(x -> User.builder()
                        .id(x.getHandle())
                        .rating(x.getRating())
                        .tier(x.getTier())
                        .build())
                .collect(Collectors.toList());

        userRepository.saveAll(users);

        return users.size();
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
