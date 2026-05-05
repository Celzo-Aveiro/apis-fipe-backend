package com.celzo_aveiro.fipe_api.interfaces.rest.dto;

import com.celzo_aveiro.fipe_api.domain.model.Marca;

public record MarcaResponse(String codigoFipe, String nome) {

    public static MarcaResponse from(Marca marca) {
        return new MarcaResponse(marca.codigoFipe(), marca.nome());
    }
}
