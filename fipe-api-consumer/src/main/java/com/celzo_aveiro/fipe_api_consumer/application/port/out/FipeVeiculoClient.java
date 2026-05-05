package com.celzo_aveiro.fipe_api_consumer.application.port.out;

import com.celzo_aveiro.fipe_api_consumer.domain.model.Marca;
import com.celzo_aveiro.fipe_api_consumer.domain.model.Veiculo;

import java.util.List;

public interface FipeVeiculoClient {

    List<Veiculo> buscarVeiculosDaMarca(Marca marca);
}
