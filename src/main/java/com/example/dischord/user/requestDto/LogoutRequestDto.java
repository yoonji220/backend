package com.example.dischord.user.requestDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogoutRequestDto {
    private String refreshToken;
}