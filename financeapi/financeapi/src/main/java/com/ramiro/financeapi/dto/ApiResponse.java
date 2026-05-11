package com.ramiro.financeapi.dto;

public class ApiResponse {
    private String message;
    private String version;

    public ApiResponse(String message, String version) {
        this.message = message;
        this.version = version;
    }

    public String getMessage() {
        return message;
    }

    public String getVersion() {
        return version;
    }

}
