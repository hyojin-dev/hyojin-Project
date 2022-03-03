package com.example.janghj.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class JwtTokenDto {
    private final String token;
    private final String username;
}
