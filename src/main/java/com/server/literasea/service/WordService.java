package com.server.literasea.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.literasea.dto.ResponseDictDto;
import com.server.literasea.dto.WordInfoDto;
import com.server.literasea.entity.Users;
import com.server.literasea.entity.Word;
import com.server.literasea.exception.UsersException;
import com.server.literasea.exception.WordException;
import com.server.literasea.repository.UserRepository;
import com.server.literasea.repository.WordRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static com.server.literasea.exception.UsersExceptionType.NOT_FOUND;
import static com.server.literasea.exception.WordExceptionType.NOT_FOUND_ID;


@RequiredArgsConstructor
@Service
public class WordService {
    //repository------------------------------------------------------------------------
    private final WordRepository wordRepository;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final EntityManager em;

    @Value("${dict.secret}")
    private String apiKey;

    //공용------------------------------------------------------------------------------------
    //TODO: 토큰에서 유저 아이디 빼기
    private Long getUserIdByToken(String token) {
        return 1l;
    }

    ;

    private Users findUsersById(Long usersId) {
        return userRepository.findById(usersId)
                .orElseThrow(() -> new UsersException(NOT_FOUND));
    }

    //wordList--------------------------------------------------------------------
    @Transactional(readOnly = true)
    public List<WordInfoDto> getWordDtoList(Users users) {
        List<Word> wordList = wordRepository.findAllByUsers(users);
        List<WordInfoDto> wordInfoDtoList = new ArrayList<>();
        for (int i = 0; i < wordList.size(); i++) {
            wordInfoDtoList.add(WordInfoDto.from(wordList.get(i)));
        }
        return wordInfoDtoList;
    }

    private List<Word> findWordListByUsers(Users users) {
        return users.getWords();
    }

    //GETword------------------------------------------------------------------------
    @Transactional(readOnly = true)
    public WordInfoDto findWordDtoByWordId(Long wordId) {
        Word word = findWordByWordId(wordId);
        return WordInfoDto.from(word);
    }

    private Word findWordByWordId(Long wordId) {
        return wordRepository.findById(wordId)
                .orElseThrow(() -> new WordException(NOT_FOUND_ID));
    }

    //POSTword---------------------------------------------------------------------------------
    @Transactional
    public String saveWord(Users logInUser, String requestWord) {
        String mean= getDefinition(requestWord);
        Word word=Word.from(requestWord, mean);
        logInUser.addWord(word);
        wordRepository.save(word);
        return mean;
    }

    //---------------------------------------------------------------------------------------
    //국립국어원 사전API
    public String getDefinition(String word) {
        String url = "https://stdict.korean.go.kr/api/search.do?key=" + apiKey
                + "&q=" + word
                + "&type_search=search"
                + "&req_type=json"; // 요청 URL

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        ResponseEntity<ResponseDictDto> response =
                restTemplate.exchange(url, HttpMethod.GET, entity, ResponseDictDto.class);
        if (response.getBody() != null && response.getBody().getChannel() != null
                && !response.getBody().getChannel().getItem().isEmpty()) {
            return response.getBody().getChannel().getItem().get(0).getSense().getDefinition();
        } else {
            return "No definition found for the word: " + word;
        }
    }
}

