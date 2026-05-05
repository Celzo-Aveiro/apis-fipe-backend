package com.celzo_aveiro.fipe_api_consumer.application.port.out;

import com.celzo_aveiro.fipe_api_consumer.domain.model.Marca;

public interface MarcaRepository {

    Marca salvar(Marca marca);
}
