package com.celzo_aveiro.fipe_api.application.dto;

public record VeiculoView(
        Long id,
        String codigoFipe,
        String modelo,
        String observacoes
) {
}
