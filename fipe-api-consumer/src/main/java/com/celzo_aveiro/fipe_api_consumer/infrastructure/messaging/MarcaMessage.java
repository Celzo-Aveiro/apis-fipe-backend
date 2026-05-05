package com.celzo_aveiro.fipe_api_consumer.infrastructure.messaging;

import com.celzo_aveiro.fipe_api_consumer.domain.model.Marca;

public record MarcaMessage(String codigoFipe, String nome) {

    public Marca toDomain() {
        return Marca.of(codigoFipe, nome);
    }
}
