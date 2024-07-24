package com.example.dischord.user.controller;


import com.example.dischord.global.response.ApiResponse;
import com.example.dischord.user.requestDto.UserSignupRequestDto;
import com.example.dischord.user.responseDto.UserResponseDto;
import com.example.dischord.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    @PostMapping("/api/user")
    public ResponseEntity<ApiResponse<?>> signupUser(@RequestBody UserSignupRequestDto requestDto) {

        UserResponseDto responseDto = userService.signupUser(requestDto);

        return ResponseEntity.ok().body(new ApiResponse<>(201, "가입 성공",responseDto));
    }

    @GetMapping("/api/user/{userId}")
    public ResponseEntity<ApiResponse<?>> getUser(@PathVariable Long userId) {

        UserResponseDto responseDto = userService.getUser(userId);

        return ResponseEntity.ok().body(new ApiResponse<>(200, "회원 정보 조회 성공",responseDto));
    }





}
