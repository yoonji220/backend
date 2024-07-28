package com.example.dischord.global.exception;

import com.example.dischord.global.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(BadRequestException.class)
    public ApiResponse<ExceptionResponse> handleBadRequestException(final BadRequestException e) {

        log.warn(e.getMessage(), e);

        return ApiResponse.error(e.getCode(), e.getMessage());
    }


    @ExceptionHandler(DuplicateException.class)
    public ApiResponse<ExceptionResponse> handleDuplicateException(final DuplicateException e) {

        log.warn(e.getMessage(), e);

        return ApiResponse.error(e.getCode(), e.getMessage());
    }

    // 추가된 부분: CustomException 예외 처리
    @ExceptionHandler(CustomException.class)
    public ApiResponse<ExceptionResponse> handleCustomException(final CustomException e) {
        log.warn(e.getMessage(), e);
        return ApiResponse.error(e.getCode().getCode(), e.getCode().getMessage());
    }

    // 추가된 부분: IllegalArgumentException 예외 처리
    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse<ExceptionResponse> handleIllegalArgumentException(final IllegalArgumentException e) {
        log.warn(e.getMessage(), e);
        return ApiResponse.error(ExceptionCode.INVALID_EMAIL_OR_PASSWORD.getCode(), e.getMessage());
    }

    // 추가된 부분: 모든 Exception 예외 처리
    @ExceptionHandler(Exception.class)
    public ApiResponse<ExceptionResponse> handleException(final Exception e) {
        log.error("Internal Server Error: ", e);
        return ApiResponse.error(ExceptionCode.TOKEN_NOT_FOUND.getCode(), "Internal Server Error");
    }
}