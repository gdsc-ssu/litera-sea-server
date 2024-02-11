package com.server.literasea.config.auth.google;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GoogleAuthService {
    @Value("${google.auth.client_id}")
    private String client_id;

    @Value("${google.auth.client_secret}")
    private String client_secret;

    @Value("${google.auth.redirect_uri}")
    private String redirect_uri;

    private final GoogleClient googleClient;

    public GetGoogleTokenResponse getTokensResponse(String code) throws RuntimeException, JsonProcessingException {
        GetTokensRequestBody getTokensRequestBody = new GetTokensRequestBody(client_id , client_secret, code, "authorization_code", redirect_uri);
        GetGoogleTokenResponse getGoogleTokenResponse = googleClient.getTokensResponse(getTokensRequestBody);
        if (getGoogleTokenResponse.getHttpStatus().equals(HttpStatus.BAD_REQUEST))
            throw new RuntimeException("구글 인증에 실패하였습니다.");

        return getGoogleTokenResponse;
    }

    public Map<String, String> getUserGoogleProfile(GetGoogleTokenResponse.TokenResponse tokenResponse) {
        HashMap<String, String> googleUserInfo = new HashMap<>();

        String reqURL = "https://www.googleapis.com/userinfo/v2/me";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + tokenResponse.getAccess_token());
        HttpEntity entity = new HttpEntity(headers);
        RestTemplate restTemplate = new RestTemplate();

        JsonNode res = restTemplate.exchange(reqURL, HttpMethod.GET, entity, JsonNode.class).getBody();

        googleUserInfo.put("email", res.get("email").toString());
        googleUserInfo.put("name", res.get("name").toString());
        googleUserInfo.put("id", res.get("id").toString());

        return googleUserInfo;
    }
}
