package com.server.literasea.controller;

import com.server.literasea.config.auth.google.GoogleAuthService;
import com.server.literasea.dto.BaseResponse;
import com.server.literasea.dto.LoginResponseDto;
import com.server.literasea.service.UserService;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/user")
public class UserController {

    private final UserService userService;
    private final GoogleAuthService googleAuthService;

    @GetMapping("/google-login")
    public BaseResponse<LoginResponseDto> googleLogin(@RequestParam("code") String code) throws Exception {
        LoginResponseDto loginResponseDto = userService.login(code);
        return BaseResponse.success("ok", loginResponseDto);
    }

}