package com.celzo_aveiro.fipe_api.application.port.out;

import com.celzo_aveiro.fipe_api.domain.model.Marca;

public interface MarcaMessagePublisher {

    void publicar(Marca marca);
}
