package com.example.backend.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private Error error;

    @Getter
    @AllArgsConstructor
    public static class Error {
        private String code;
        private String message;
    }

    public static ErrorResponse from(ErrorCode errorCode) {
        return new ErrorResponse(new Error(errorCode.name(), errorCode.getDefaultMessage()));
    }

    public static ErrorResponse from(ErrorCode errorCode, String customMessage) {
        return new ErrorResponse(new Error(errorCode.name(), customMessage));
    }
}
