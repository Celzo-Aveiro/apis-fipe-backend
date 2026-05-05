package com.celzo_aveiro.fipe_api.interfaces.rest.dto;

import jakarta.validation.constraints.NotBlank;

public record TokenRequest(

        @NotBlank(message = "username é obrigatório")
        String username,

        @NotBlank(message = "password é obrigatório")
        String password
) {
}
