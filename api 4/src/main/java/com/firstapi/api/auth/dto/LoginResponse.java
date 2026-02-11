package com.firstapi.api.auth.dto;


public record LoginResponse(
        String accessToken,
        String tokenType
) {}

