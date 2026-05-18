package com.ramiro.financeapi.dto;

public class RefreshResponse {

    private String accessToken;

    public RefreshResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
