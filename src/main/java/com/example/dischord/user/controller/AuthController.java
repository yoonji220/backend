package com.example.dischord.user.controller;

import com.example.dischord.global.response.ApiResponse;
import com.example.dischord.user.requestDto.LoginRequestDto;
import com.example.dischord.user.requestDto.LogoutRequestDto;
import com.example.dischord.user.responseDto.LoginResponseDto;
import com.example.dischord.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/api/login")
    public ApiResponse<LoginResponseDto> loginUser(@RequestBody LoginRequestDto requestDto) {
        LoginResponseDto responseDto = authService.loginUser(requestDto);
        return ApiResponse.ok(responseDto);
    }

    @PostMapping("/api/logout")
    public ApiResponse<?> logoutUser(@RequestHeader("Authorization") String token, @RequestBody LogoutRequestDto requestDto) {
        authService.logoutUser(token, requestDto.getRefreshToken());
        return ApiResponse.ok("Success Logout");
    }
}