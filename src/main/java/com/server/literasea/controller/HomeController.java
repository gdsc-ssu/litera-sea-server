package com.server.literasea.controller;

import static com.server.literasea.enums.RequestUri.HOME_URI;

import com.server.literasea.dto.BadgeInfoDto;
import com.server.literasea.dto.BaseResponse;
import com.server.literasea.dto.InventoryInfoDto;
import com.server.literasea.dto.ResponseMainPageDto;
import com.server.literasea.entity.Users;
import com.server.literasea.repository.UserRepository;
import com.server.literasea.service.HomeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(HOME_URI)
@Tag(name="HOME", description = "홈화면 관련 api")
public class HomeController {
    private final HomeService homeService;
    private final UserRepository userRepository;

    @Operation(summary="메인페이지 불러오기")
    @ApiResponse(responseCode = "200", description = "DTO형식으로 정보 반환")
    @GetMapping("/main")
    public ResponseEntity<ResponseMainPageDto> getMainPage(@AuthenticationPrincipal Users user){
        return ResponseEntity.ok().body(homeService.getMainPageDtoByUsers(user));
    }

    @Operation(summary="뱃지 보관함 불러오기")
    @ApiResponse(responseCode = "200", description = "뱃지 DTO 리스트 반환")
    @GetMapping("/badgeInfo")
    public ResponseEntity<List<BadgeInfoDto>> getBadgeInfo(@AuthenticationPrincipal Users user){
        return ResponseEntity.ok().body(homeService.getBadgeInfoDtoListByUsers(user));
    }

    @Operation(summary="배 조각 보관함 불러오기")
    @ApiResponse(responseCode = "200", description = "배 조각 정보 DTO 반환")
    @GetMapping("/boatInfo")
    public ResponseEntity<InventoryInfoDto> getBoatInfo(@AuthenticationPrincipal Users user){
        return ResponseEntity.ok(homeService.getInventoryInfoDto(user));
    }
}
