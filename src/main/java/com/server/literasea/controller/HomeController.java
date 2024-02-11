package com.server.literasea.controller;

import com.server.literasea.dto.BadgeInfoDto;
import com.server.literasea.dto.BaseResponse;
import com.server.literasea.dto.ResponseMainPageDto;
import com.server.literasea.entity.Users;
import com.server.literasea.repository.UserRepository;
import com.server.literasea.service.HomeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/home")
@Tag(name="HOME", description = "홈화면 관련 api")
public class HomeController {
    private final HomeService homeService;

    //TODO: 지워야됨 테스트용
    private final UserRepository userRepository;
    //지워야함

    @GetMapping("/test")
    public BaseResponse<String> test() {
        return BaseResponse.success("ok", "Hello, world!");
    }

    @Operation(summary="메인페이지 불러오기")
    @ApiResponse(responseCode = "200", description = "DTO형식으로 정보 반환")
    @GetMapping()
    public ResponseEntity<ResponseMainPageDto> mainPage(){
        //TODO: 유저 불러오기, 유저 못찾을시 오류문 핸들러
        Users users =userRepository.findById(Long.valueOf(1)).get();
        return ResponseEntity.ok().body(homeService.getResponseMainPageDto(users));
    }

    @Operation(summary="뱃지 보관함 불러오기")
    @ApiResponse(responseCode = "200", description = "뱃지 DTO 리스트 반환")
    @GetMapping("/badgeInfo")
    public ResponseEntity<List<BadgeInfoDto>> badgeInfo(){
        //TODO: 유저 불러오기, 유저 못찾을시 오류문 핸들러
        Users users =new Users();
        return ResponseEntity.ok().body(homeService.getBadgeInfoDtoList(users));
    }

    @Operation(summary="배 조각 보관함 불러오기")
    @ApiResponse(responseCode = "200", description = "배 조각 정보 DTO 반환")
    @GetMapping("/boatInfo")
    public ResponseEntity<?> boatInfo(){
        //TODO: 유저 불러오기, 유저 못찾을시 오류문 핸들러
        Users users =new Users();
        return ResponseEntity.ok().body(homeService.getBoatInfoDto(users));
    }
}
