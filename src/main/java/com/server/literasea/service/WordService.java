package com.server.literasea.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.util.Value;
import com.server.literasea.dto.ResponseDictDto;
import com.server.literasea.dto.WordInfoDto;
import com.server.literasea.entity.Users;
import com.server.literasea.entity.Word;
import com.server.literasea.exception.UsersException;
import com.server.literasea.exception.WordException;
import com.server.literasea.repository.UserRepository;
import com.server.literasea.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

import static com.server.literasea.exception.UsersExceptionType.NOT_FOUND;
import static com.server.literasea.exception.WordExceptionType.NOT_FOUND_ID;

@RequiredArgsConstructor
@Service
public class WordService {
    //repository------------------------------------------------------------------------
    @Value("${stdict.secret}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final WordRepository wordRepository;
    private final UserRepository userRepository;
    //공용------------------------------------------------------------------------------------
    //TODO: 토큰에서 유저 아이디 빼기
    private Long getUserIdByToken(String token){return 1l;};
    private Users findUsersById(Long usersId){
        return userRepository.findById(usersId)
                .orElseThrow(() -> new UsersException(NOT_FOUND));
    }
    //wordList--------------------------------------------------------------------

    @Transactional(readOnly = true)
    public List<WordInfoDto> getWordDtoList(Users users) {
        List<Word> wordList=wordRepository.findAllByUsers(users);
        List<WordInfoDto> wordInfoDtoList=new ArrayList<>();
        for(int i=0;i<wordList.size();i++){
            wordInfoDtoList.add(WordInfoDto.from(wordList.get(i)));
        }
        return wordInfoDtoList;
    }

    private List<Word> findWordListByUsers(Users users){
        return users.getWords();
    }
    //GETword------------------------------------------------------------------------
    @Transactional(readOnly = true)
    public WordInfoDto findWordDtoByWordId(Long wordId){
        Word word= findWordByWordId(wordId);
        return WordInfoDto.from(word);
    }
    private Word findWordByWordId(Long wordId){
        return wordRepository.findById(wordId)
                .orElseThrow(()->new WordException(NOT_FOUND_ID));
    }
    //POSTword---------------------------------------------------------------------------------
    @Transactional
    public String saveWord(Users logInUser, String requestWord) {
        Users user=findUsersById(logInUser.getId());  //이거 왜 다이렉트로 매개변수로 받은 User쓰면 안됨?
        String mean=getFirstDefinition(requestWord);
        Word word=Word.from(requestWord, mean);
        user.addWord(word);
        wordRepository.save(word);
        return mean;  //이거 Word로 리턴하면 왜 오류?
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

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        System.out.println(response);

//        if (response.getBody() != null && response.getBody().getChannel() != null
//                && !response.getBody().getChannel().getItem().isEmpty()) {
//            // 응답에서 'message'의 'content' 추출
//            return response.getBody().getChannel().getItem().get(0).getSense().getDefinition();
//        } else {
//            return "No definition found for the word: " + word;
//        }
        return "";
    }

    public String getDefinition2(String word) {
        // 검색어를 URL 인코딩합니다.
        String encodedWord = UriComponentsBuilder.fromHttpUrl("https://stdict.korean.go.kr/api/search.do")
                .queryParam("key", apiKey)
                .queryParam("q", word)
                .queryParam("req_type", "json")
                .toUriString();

        // GET 요청을 보내고 응답을 받습니다.
        ResponseEntity<ResponseDictDto> response = restTemplate.getForEntity(encodedWord, ResponseDictDto.class);

        // 응답에서 필요한 정보를 추출합니다.
        if (response.getBody() != null && response.getBody().getChannel() != null
                && !response.getBody().getChannel().getItem().isEmpty()) {
            // 응답에서 'message'의 'content' 추출
            return response.getBody().getChannel().getItem().get(0).getSense().getDefinition();
        } else {
            return "No definition found for the word: " + word;
        }
    }

    public String getFirstDefinition(String word) {
        try {
            URL url = new URL("https://stdict.korean.go.kr/api/search.do?key=" + apiKey
                    + "&type_search=search&q=" + word);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getValue(String tag, Element element) {
        NodeList nl = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node value = (Node) nl.item(0);
        return value.getNodeValue();
    }

    public List<String> searchWordAndGetDefinition(String word) {
        String urlTemplate = UriComponentsBuilder.fromHttpUrl("https://stdict.korean.go.kr/api/search.do")
                .queryParam("key", apiKey)
                .queryParam("q", word)
                .queryParam("req_type", "json")
                .toUriString();
        System.out.println("응답받기전");
        ResponseEntity<String> response = restTemplate.getForEntity(urlTemplate, String.class);
        System.out.println("응답받은후");
        List<String> definitions = new ArrayList<>();
        // 응답에서 필요한 데이터 추출
        try {
            JsonNode rootNode = objectMapper.readTree(response.getBody());
            JsonNode items = rootNode.path("channel").path("item");
            for (JsonNode item : items) {
                JsonNode sense = item.path("sense");
                if (!sense.isMissingNode()) { // sense 노드가 존재할 때만 처리
                    String definition = sense.path("definition").asText();
                    definitions.add(definition);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("-----------집중------------");
        System.out.println("definitions = " + definitions.get(0));
        return definitions;
    }
}
