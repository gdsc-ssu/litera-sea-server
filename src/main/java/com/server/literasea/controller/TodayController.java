package com.server.literasea.controller;

import static com.server.literasea.enums.RequestUri.TODAY_URI;

import com.server.literasea.dto.BaseResponse;
import com.server.literasea.dto.SummaryResultDto;
import com.server.literasea.dto.TodayArticleDto;
import com.server.literasea.dto.UserSummaryDto;
import com.server.literasea.entity.Users;
import com.server.literasea.service.TodayService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(TODAY_URI)
public class TodayController {
    private final TodayService todayService;

    @GetMapping("/")
    @Operation(summary = "오늘 요약 할 지문 5개 불러오기", description = "로그인은 추후 추가 예정입니다.")
    public BaseResponse<TodayArticleDto> todayGet(@AuthenticationPrincipal Users user) {
        TodayArticleDto todayArticleDto = todayService.findTodayArticles(user);
        return BaseResponse.success("ok", todayArticleDto);
    }

    @PostMapping("/post")
    @Operation(summary = "유저가 작성한 요약 전송하고 결과 불러오기", description = "AI 정해지면 개발 예정입니다.")
    public BaseResponse<SummaryResultDto> todayPost(@AuthenticationPrincipal Users user, @RequestBody UserSummaryDto userSummaryDto) throws IOException {
        SummaryResultDto summaryResultDto = todayService.todayPost(user, userSummaryDto);
        return BaseResponse.success("ok", summaryResultDto);
    }
}
