package com.celzo_aveiro.fipe_api.interfaces.rest.dto;

import com.celzo_aveiro.fipe_api.application.dto.VeiculoView;

public record VeiculoResponse(
        Long id,
        String codigoFipe,
        String modelo,
        String observacoes
) {

    public static VeiculoResponse from(VeiculoView view) {
        return new VeiculoResponse(view.id(), view.codigoFipe(), view.modelo(), view.observacoes());
    }
}
