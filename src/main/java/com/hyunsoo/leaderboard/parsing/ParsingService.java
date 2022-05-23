package com.hyunsoo.leaderboard.parsing;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParsingService {

    private static final String baekJoonUrl = "https://www.acmicpc.net/school/ranklist/309";

    private static final String sovedAcApiUrl = "https://solved.ac/api/v3/user/show?handle={userId}";

    private final RestTemplate restTemplate;

    /**
     * @return pagenation 수
     * @throws IOException
     * 백준 성공회대학교 랭크 url을 크롤링해서 페이지 수를 반환하는 메소드
     */
    public int getPageNumber() throws IOException {
        Document doc = Jsoup.connect(baekJoonUrl).get();
        Elements elements = doc.select(".pagination li");

        return Integer.parseInt(elements.get(elements.size() - 2).text());
    }

    /**
     * @return userId 리스트
     * @throws IOException
     * 페이지마다 존재하는 userId들을 크롤링해서 리스트로 반환
     */
    public List<String> getAllUserIds() throws IOException {
        int pageNumber = getPageNumber();

        Elements elements = new Elements();

        for (int i = 1; i <= pageNumber; i++) {
            Document doc = Jsoup.connect(baekJoonUrl + "/" + i).get();
            elements.addAll(doc.select("#ranklist td:nth-child(2) a"));
        }

        return elements.eachText();
    }

    /**
     * @param userId user ID
     * @return UserResponse
     * UserId로 soved.ac api에 정보를 불러와 반환
     */
    public UserParsingData getUserInfo(String userId) {
        try {
            return restTemplate.getForObject(sovedAcApiUrl, UserParsingData.class, userId);
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }
    }
}
