package com.celzo_aveiro.fipe_api.application.usecase;

import com.celzo_aveiro.fipe_api.application.dto.VeiculoView;

import java.util.List;

public interface ListarVeiculosPorMarca {

    List<VeiculoView> listar(String codigoFipeMarca);
}
