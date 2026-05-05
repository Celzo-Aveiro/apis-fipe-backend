package com.celzo_aveiro.fipe_api.interfaces.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AtualizarVeiculoRequest(

        @NotBlank(message = "modelo é obrigatório")
        @Size(max = 255, message = "modelo deve ter no máximo 255 caracteres")
        String modelo,

        String observacoes
) {
}
