package com.server.literasea.service;

import com.server.literasea.config.auth.JwtService;
import com.server.literasea.config.auth.google.GetGoogleTokenResponse;
import com.server.literasea.config.auth.google.GoogleAuthService;
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

        Map<String, String> googleUserInfo = googleAuthService.getUserGoogleProfile(getGoogleTokenResponse.getTokenResponse());

        Users users = userRepository.findByEmail(googleUserInfo.get("email")).orElseGet(
                () -> saveUser(googleUserInfo)
        );

        String serviceAccessToken = jwtService.createAccessToken(googleUserInfo.get("email"));
        String serviceRefreshToken = jwtService.createRefreshToken(googleUserInfo.get("email"));

        return LoginResponseDto.builder()
                .accessToken(serviceAccessToken)
                .refreshToken(serviceRefreshToken)
                .build();
    }

    @Transactional
    public LoginResponseDto login(String userName) {

        Map<String, String> userInfo = new HashMap<>();

        userInfo.put("email", "test@test.com");
        userInfo.put("name", userName);

        Users users = userRepository.findByEmail("test@test.com").orElseGet(
                () -> saveUser(userInfo)
        );

        String serviceAccessToken = jwtService.createAccessToken(userInfo.get("email"));
        String serviceRefreshToken = jwtService.createRefreshToken(userInfo.get("email"));

        return LoginResponseDto.builder()
                .accessToken(serviceAccessToken)
                .refreshToken(serviceRefreshToken)
                .build();
    }

    @Transactional
    public Users saveUser(Map<String, String> googleUserInfo) {
        Users user = Users.builder()
                .email(googleUserInfo.get("email"))
                .nickname(googleUserInfo.get("name"))
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
