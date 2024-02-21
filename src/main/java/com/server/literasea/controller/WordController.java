package com.server.literasea.controller;

import static com.server.literasea.enums.RequestUri.WORD_URI;

import com.server.literasea.dto.BaseResponse;
import com.server.literasea.dto.WordInfoDto;
import com.server.literasea.entity.Users;
import com.server.literasea.entity.Word;
import com.server.literasea.service.WordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(WORD_URI)
@Tag(name="WORD", description = "단어 기능 관련 api")
public class WordController {

    private final WordService wordService;


    @PostMapping("/{requestWord}")
    @Operation(summary="단어 저장하기")
    @ApiResponse(responseCode = "201", description = "단어 저장 완료")
    //TODO: requestWord 왜 PathVariable은되고, RequestBody는 안되느닞 정리하기
    public ResponseEntity<String> saveWordByDto(@AuthenticationPrincipal Users user,
                                              @PathVariable("requestWord") String requestWord){

        return ResponseEntity.status(HttpStatus.CREATED).
                body(wordService.saveWord(user, requestWord));
    }

     */

    @GetMapping("/wordList")
    @Operation(summary="유저가 저장한 단어 리스트 가져오기")
    @ApiResponse(responseCode = "200", description = "DTO 리스트형식으로 정보 반환")
    public ResponseEntity<List<WordInfoDto>> getWordDtoListByUsers(@AuthenticationPrincipal Users user){
        return ResponseEntity.ok(wordService.getWordDtoList(user));
    }

    @GetMapping("/{wordId}")
    @Operation(summary="word_id로 단어 가져오기")
    @ApiResponse(responseCode = "200", description = "DTO형식으로 정보 반환")
    public ResponseEntity<WordInfoDto> getWordDtoByWordId(@AuthenticationPrincipal Users user,
                                                          @PathVariable Long wordId){
        return ResponseEntity.ok(wordService.findWordDtoByWordId(wordId));
    }

    @GetMapping("/apiTest/{word}")
    @Operation(summary="apiTEST")
    @ApiResponse(responseCode = "200", description = "DTO형식으로 정보 반환")
    public ResponseEntity<?> testAPi(@AuthenticationPrincipal Users user,
                                     @PathVariable String word){

        return ResponseEntity.ok(wordService.getDefinition(word));
    }
}
