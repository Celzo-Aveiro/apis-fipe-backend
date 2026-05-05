package com.celzo_aveiro.fipe_api.interfaces.rest.dto;

public record TokenResponse(
        String accessToken,
        String tokenType,
        long expiresIn
) {
}
