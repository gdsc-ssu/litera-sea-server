package com.server.literasea.service;

import com.server.literasea.config.auth.JwtService;
import com.server.literasea.config.auth.google.GetGoogleTokenResponse;
import com.server.literasea.config.auth.google.GoogleAuthService;
import com.server.literasea.config.auth.google.UserInfo;
import com.server.literasea.dto.LoginResponseDto;
import com.server.literasea.entity.Inventory;
import com.server.literasea.entity.Users;
import com.server.literasea.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
@Service
public class UserService {
    private final GoogleAuthService googleAuthService;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Transactional
    public LoginResponseDto googleLogin(String code) throws Exception {
        GetGoogleTokenResponse getGoogleTokenResponse = googleAuthService.getTokensResponse(code);

        UserInfo googleUserInfo = googleAuthService.getUserGoogleProfile(getGoogleTokenResponse.getTokenResponse());

        Users users = userRepository.findByEmail(googleUserInfo.getEmail()).orElseGet(
                () -> saveUser(googleUserInfo)
        );

        String serviceAccessToken = jwtService.createAccessToken(googleUserInfo.getEmail());
        String serviceRefreshToken = jwtService.createRefreshToken(googleUserInfo.getEmail());

        return LoginResponseDto.builder()
                .accessToken(serviceAccessToken)
                .refreshToken(serviceRefreshToken)
                .build();
    }

    @Transactional
    public LoginResponseDto login(String userName) {

        UserInfo userInfo = new UserInfo(userName, "test@test.com");

        Users users = userRepository.findByEmail("test@test.com").orElseGet(
                () -> saveUser(userInfo)
        );

        String serviceAccessToken = jwtService.createAccessToken(userInfo.getEmail());
        String serviceRefreshToken = jwtService.createRefreshToken(userInfo.getEmail());

        return LoginResponseDto.builder()
                .accessToken(serviceAccessToken)
                .refreshToken(serviceRefreshToken)
                .build();
    }

    @Transactional
    public Users saveUser(UserInfo googleUserInfo) {
        Users user = Users.builder()
                .email(googleUserInfo.getEmail())
                .nickname(googleUserInfo.getName())
                .day(0L)
                .exp(0)
                .grammarScore(0)
                .subjectScore(0)
                .vocaScore(0)
                .level(0)
                .location(0)
                .inventory(new Inventory())
                .build();
        userRepository.saveAndFlush(user);
        return user;
    }
}
