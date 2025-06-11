package com.example.backend.domain.auth.dto.response;

import lombok.Getter;

@Getter
public class SigninResponse {
    private final String accessToken;

    public SigninResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
