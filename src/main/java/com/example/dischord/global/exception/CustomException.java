package com.example.dischord.global.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final ExceptionCode code;

    public CustomException(ExceptionCode code) {
        super(code.getMessage());
        this.code = code;
    }
}