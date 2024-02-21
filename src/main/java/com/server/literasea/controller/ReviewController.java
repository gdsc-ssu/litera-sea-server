package com.server.literasea.controller;

import static com.server.literasea.enums.RequestUri.REVIEW_URI;

import com.server.literasea.dto.BaseResponse;
import com.server.literasea.dto.ResponseReviewDto;
import com.server.literasea.entity.Users;
import com.server.literasea.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(REVIEW_URI)
@Tag(name="REVIEW", description = "리뷰 관련 api")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/dayList")
    @Operation(summary="유저들이 지금까지 문제 푼 날짜들 반환")
    @ApiResponse(responseCode = "200", description = "날짜를 String형으로 List반환")
    public ResponseEntity<List<String>> getSolveDateListByUser(@AuthenticationPrincipal Users user){
        return ResponseEntity.ok(reviewService.getSolveDateListByUser(user));
    }

    @GetMapping("/solveIdList/{createAt}")
    @Operation(summary="선택한 날짜에 푼 문제들의 SolveId리스트 반환")
    @ApiResponse(responseCode = "200", description = "Long타입 List반환")
    public ResponseEntity<List<Long>> getSolveIdListByDay(
            @AuthenticationPrincipal Users user,
            @PathVariable String createAt){
        return ResponseEntity.ok(reviewService.getSolveIdListByDay(user, createAt));
    }

    @GetMapping("/{solveId}")
    @Operation(summary="solveId로 리뷰 정보 DTO반환받기")
    @ApiResponse(responseCode = "200", description = "DTO형식으로 정보 반환")
    public ResponseEntity<ResponseReviewDto> getReviewDto(@AuthenticationPrincipal Users user,
                                                          @PathVariable("solveId") Long solveId){
        return ResponseEntity.ok(reviewService.getReviewDtoBySolveId(solveId));
    }
}
