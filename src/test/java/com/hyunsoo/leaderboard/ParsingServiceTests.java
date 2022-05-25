package com.hyunsoo.leaderboard;

import com.hyunsoo.leaderboard.parsing.ParsingService;
import com.hyunsoo.leaderboard.parsing.UserParsingData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class ParsingServiceTests {

    @Spy
    private RestTemplate restTemplate;

    @InjectMocks
    private ParsingService parsingService;

    @Test
    @DisplayName("성공회대학교 랭크 url을 크롤링해서 페이지 수 반환")
    void getPageNumber() throws IOException {
        // when
        Integer pageNumber = parsingService.getPageNumber();

        // then
        assertNotNull(pageNumber);
    }

    @Test
    @DisplayName("성공회대학교에 소속된 모든 백준 아이디 반환")
    void getUserIdAll() throws IOException {
        // when
        List<String> list = parsingService.getAllUserIds();

        // then
        assertNotNull(list);
    }

    @Test
    @DisplayName("해당 유저의 정보 반환")
    void getInfoByUserId() {
        // given
        String userId = "gustn8523";

        // when
        UserParsingData data = parsingService.getUserInfo(userId);

        // then
        assertEquals(data.getHandle(), userId);
    }
}
