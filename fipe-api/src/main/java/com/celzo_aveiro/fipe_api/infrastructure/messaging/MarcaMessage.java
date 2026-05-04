package com.celzo_aveiro.fipe_api.infrastructure.messaging;

import com.celzo_aveiro.fipe_api.domain.model.Marca;

public record MarcaMessage(String codigoFipe, String nome) {

    static MarcaMessage from(Marca marca) {
        return new MarcaMessage(marca.codigoFipe(), marca.nome());
    }
}
