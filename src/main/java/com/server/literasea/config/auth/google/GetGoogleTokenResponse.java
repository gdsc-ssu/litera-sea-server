package com.server.literasea.config.auth.google;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
public class GetGoogleTokenResponse {
    HttpStatus httpStatus;
    TokenResponse tokenResponse;

    @Getter
    @Setter
    @ToString
    public static class TokenResponse {
        String access_token;
        Long expires_in;
        String id_token;
        String scope;
        String token_type;
    }
}
