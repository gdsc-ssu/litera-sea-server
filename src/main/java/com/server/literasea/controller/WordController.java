package com.server.literasea.controller;

import com.server.literasea.dto.BaseResponse;
import com.server.literasea.entity.Users;
import com.server.literasea.service.WordService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/word")
public class WordController {

    private final WordService wordService;

    @GetMapping("/test")
    public BaseResponse<String> test(@AuthenticationPrincipal Users user) {
        System.out.println(user.getEmail()); // 로그인 테스트
        return BaseResponse.success("ok", "Hello, world!");
    }
}
