package com.celzo_aveiro.fipe_api.application.port.out;

import com.celzo_aveiro.fipe_api.domain.model.Marca;

import java.util.List;

public interface MarcaConsultaRepository {

    List<Marca> listarTodas();

    boolean existePorCodigoFipe(String codigoFipe);
}
