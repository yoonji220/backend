package com.example.dischord.user.service;

import com.example.dischord.global.jwt.JwtUtil;
import com.example.dischord.user.requestDto.LoginRequestDto;
import com.example.dischord.user.responseDto.LoginResponseDto;
import com.example.dischord.user.entity.User;
import com.example.dischord.global.exception.CustomException;
import com.example.dischord.global.exception.ExceptionCode;
import com.example.dischord.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public LoginResponseDto loginUser(LoginRequestDto requestDto) {
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_USER_ID));

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new CustomException(ExceptionCode.INVALID_PASSWORD);
        }

        String accessToken = jwtUtil.createAccessToken(user.getEmail());
        String refreshToken = jwtUtil.createRefreshToken(user.getEmail());

        return new LoginResponseDto(accessToken, refreshToken);
    }
}