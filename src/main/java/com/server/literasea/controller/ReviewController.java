package com.server.literasea.controller;

import com.server.literasea.dto.BaseResponse;
import com.server.literasea.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/test")
    public BaseResponse<String> test() {
        return BaseResponse.success("ok", "Hello, world!");
    }
}
