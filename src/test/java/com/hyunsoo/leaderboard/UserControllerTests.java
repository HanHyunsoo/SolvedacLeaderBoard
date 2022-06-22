package com.hyunsoo.leaderboard;

import com.hyunsoo.leaderboard.controller.UserController;
import com.hyunsoo.leaderboard.dto.UserResponse;
import com.hyunsoo.leaderboard.model.User;
import com.hyunsoo.leaderboard.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserControllerTests {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    @DisplayName("한명의 User Read 성공 테스트")
    void findUserByIdSuccessTest() {
        // given
        final UserResponse testDto = getTestUserDto();

        given(userService.findById(testDto.getUserId())).willReturn(testDto);

        // when
        ResponseEntity<UserResponse> responseEntity = userController.getUserById(testDto.getUserId());

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(testDto.getUserId(), Objects.requireNonNull(responseEntity.getBody()).getUserId());
        verify(userService).findById(anyString());
    }

    @Test
    @DisplayName("한명의 User Read 실패 테스트")
    void FromFindUserByIdFailureTest() {
        // given
        given(userService.findById(anyString())).willThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        // when

        // then
        assertThrows(ResponseStatusException.class, () -> userController.getUserById(anyString()));
        verify(userService).findById(anyString());
    }

    @Test
    @DisplayName("여러명의 User Read 성공 테스트 1(값이 있을 경우)")
    void findAllUserSuccessTest1() {
        // given
        final List<UserResponse> testDtoList = getTestUserDtoList();

        given(userService.findAll()).willReturn(testDtoList);

        // when
        ResponseEntity<List<UserResponse>> responseEntity = userController.findAllUser();

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(testDtoList.get(0), Objects.requireNonNull(responseEntity.getBody()).get(0));
        verify(userService).findAll();
    }

    @Test
    @DisplayName("여러명의 User Read 성공 테스트 2(값이 비어있을 경우)")
    void findAllUserSuccessTest2() {
        // given
        final List<UserResponse> testDtoList = new ArrayList<>();

        given(userService.findAll()).willReturn(testDtoList);

        // when
        ResponseEntity<List<UserResponse>> responseEntity = userController.findAllUser();

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(userService).findAll();
    }

    public UserResponse getTestUserDto() {
        User user = User.builder()
                .id("test")
                .rating(1000L)
                .tier((short) 5)
                .build();
        Long rank = 1L;
        return new UserResponse(user, rank);
    }

    public List<UserResponse> getTestUserDtoList() {
        List<UserResponse> list = new ArrayList<>();

        for (int i = 1; i <= 100; i++) {
            User user = User.builder()
                    .id("test" + i)
                    .rating((long) (1000 + i))
                    .tier((short) (Math.random() * 32))
                    .build();
            list.add(new UserResponse(user, (long) i));
        }

        return list;
    }
}
