package com.server.literasea.controller;

import com.server.literasea.dto.BaseResponse;
import com.server.literasea.service.TodayService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/today")
public class TodayController {
    private final TodayService todayService;

    @GetMapping("/")
    @Operation(summary = "오늘 요약 할 지문 5개 불러오기", description = "현재 API 사용을 위해 로그인이 필요없습니다.")
    public BaseResponse<String> todayGet() {
        return BaseResponse.success("ok", "Hello, world!");
    }

    @PostMapping("/post")
    @Operation(summary = "유저가 작성한 요약 전송하고 결과 불러오기", description = "현재 API 사용을 위해 로그인이 필요없습니다.")
    public BaseResponse<String> todayPost() {
        return BaseResponse.success("ok", "Hello, world!");
    }
}
