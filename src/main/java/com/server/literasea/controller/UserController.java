package com.server.literasea.controller;

import static com.server.literasea.enums.RequestUri.USER_URI;

import com.server.literasea.config.auth.google.GoogleAuthService;
import com.server.literasea.dto.BaseResponse;
import com.server.literasea.dto.LoginResponseDto;
import com.server.literasea.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(USER_URI)
public class UserController {

    private final UserService userService;

    @GetMapping("/google-login")
    @Operation(summary = "구글로그인 API", description = "구글로그인을 통해 얻은 code를 넣어주세요.")
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