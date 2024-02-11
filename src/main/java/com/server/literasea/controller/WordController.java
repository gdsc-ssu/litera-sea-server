package com.server.literasea.controller;

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
@RequestMapping("/api/v1/word")
@Tag(name="WORD", description = "단어 기능 관련 api")
public class WordController {

    private final WordService wordService;

    @GetMapping("/test")
    public BaseResponse<String> test(@AuthenticationPrincipal Users user) {
        System.out.println(user.getEmail()); // 로그인 테스트
        return BaseResponse.success("ok", "Hello, world!");
    }

    @PostMapping
    @Operation(summary="단어 저장하기")
    @ApiResponse(responseCode = "201", description = "단어 저장 완료")
    public ResponseEntity<Word> saveWordByDto(@RequestBody WordInfoDto wordInfoDto){
        //TODO: 유저 불러오기
        Users users;
        return ResponseEntity.status(HttpStatus.CREATED).
                body(wordService.saveWord(wordInfoDto));
    }

    @GetMapping("/wordList")
    @Operation(summary="유저가 저장한 단어 리스트 가져오기")
    @ApiResponse(responseCode = "200", description = "DTO 리스트형식으로 정보 반환")
    public ResponseEntity<List<WordInfoDto>> getWordDtoListByUsers(){
        //TODO: 유저 불러오기
        Users users=new Users();
        return ResponseEntity.ok(wordService.getWordDtoList(users));
    }

    @GetMapping()
    @Operation(summary="word_id로 단어 가져오기")
    @ApiResponse(responseCode = "200", description = "DTO형식으로 정보 반환")
    public ResponseEntity<WordInfoDto> getWordDtoByWordId(@PathVariable Long wordId) throws Exception {
        return ResponseEntity.ok(wordService.findWordDtoByWordId(wordId));
    }
}
