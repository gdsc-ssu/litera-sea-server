package com.server.literasea.controller;

import com.server.literasea.config.auth.google.GoogleAuthService;
import com.server.literasea.dto.BaseResponse;
import com.server.literasea.dto.LoginResponseDto;
import com.server.literasea.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/user")
public class UserController {

    private final UserService userService;
    private final GoogleAuthService googleAuthService;

    @GetMapping("/google-login")
    @Operation(summary = "구글로그인 API", description = "아직 배포환경에서는 정상작동 하지 않습니다. 임시 로그인을 이용해서 토큰을 발급받아 주세요.")
    public BaseResponse<LoginResponseDto> googleLogin(@RequestParam("code") String code) throws Exception {
        LoginResponseDto loginResponseDto = userService.googleLogin(code);
        return BaseResponse.success("ok", loginResponseDto);
    }

    @GetMapping("/login")
    @Operation(summary = "임시 로그인 API", description = "API를 호출하면 테스트 계정이 만들어지고, 토큰이 발급됩니다.")
    public BaseResponse<LoginResponseDto> login() throws Exception {
        LoginResponseDto loginResponseDto = userService.login("test");
        return BaseResponse.success("ok", loginResponseDto);
    }

}