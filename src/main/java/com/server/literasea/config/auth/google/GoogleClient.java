package com.server.literasea.config.auth.google;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class GoogleClient {
    @Value("${google.auth.url}")
    private String googleAuthUrl;

    public GetGoogleTokenResponse getTokensResponse(GetTokensRequestBody getTokensRequestBody) throws JsonProcessingException {
        GetGoogleTokenResponse getGoogleTokenResponse = new GetGoogleTokenResponse();
        ObjectMapper objectMapper = new ObjectMapper();

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", getTokensRequestBody.getClient_id());
        params.add("client_secret", getTokensRequestBody.getClient_secret());
        params.add("code", getTokensRequestBody.getCode());
        params.add("grant_type", getTokensRequestBody.getGrant_type());
        params.add("redirect_uri", getTokensRequestBody.getRedirect_uri());

        HttpEntity<MultiValueMap<String, String>> accessTokenRequest = new HttpEntity<>(params, headers);

        ResponseEntity<String> responseEntity = rt.exchange(
                googleAuthUrl + "/token",
                HttpMethod.POST,
                accessTokenRequest,
                String.class
        );

        getGoogleTokenResponse.setHttpStatus((HttpStatus) responseEntity.getStatusCode());
        if (getGoogleTokenResponse.getHttpStatus().equals(HttpStatus.OK)) {
            getGoogleTokenResponse.setTokenResponse(objectMapper.readValue(responseEntity.getBody(), GetGoogleTokenResponse.TokenResponse.class));
        }

        return getGoogleTokenResponse;
    }
}
