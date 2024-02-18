package com.server.literasea.controller;

import static com.server.literasea.enums.RequestUri.TODAY_URI;

import com.server.literasea.dto.BaseResponse;
import com.server.literasea.dto.TodayArticleDto;
import com.server.literasea.entity.Users;
import com.server.literasea.service.TodayService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(TODAY_URI)
public class TodayController {
    private final TodayService todayService;

    @GetMapping("/")
    @Operation(summary = "오늘 요약 할 지문 5개 불러오기", description = "로그인은 추후 추가 예정입니다.")
    public BaseResponse<List<TodayArticleDto>> todayGet(@AuthenticationPrincipal Users user) {
        List<TodayArticleDto> todayArticleDtos = todayService.findTodayArticles(user);
        return BaseResponse.success("ok", todayArticleDtos);
    }

    @PostMapping("/post")
    @Operation(summary = "유저가 작성한 요약 전송하고 결과 불러오기", description = "AI 정해지면 개발 예정입니다.")
    public BaseResponse<String> todayPost(@AuthenticationPrincipal Users user) {
        // 미구현 상태입니다.
        return BaseResponse.success("ok", "Hello, world!");
    }
}
