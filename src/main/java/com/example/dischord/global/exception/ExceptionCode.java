package com.example.dischord.global.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionCode {

    NOT_FOUND_USER_ID(404, "해당하는 유저가 존재하지 않습니다."),


    INVALID_PASSWORD(2001, "비밀번호가 일치하지 않습니다.");

    private final int code;
    private final String message;
}
