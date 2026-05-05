package com.celzo_aveiro.fipe_api_consumer.application.usecase;

import com.celzo_aveiro.fipe_api_consumer.domain.model.Marca;

public interface ImportarVeiculosDaMarca {

    void importar(Marca marca);
}
