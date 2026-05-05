package com.celzo_aveiro.fipe_api.application.port.out;

import com.celzo_aveiro.fipe_api.application.dto.VeiculoView;
import com.celzo_aveiro.fipe_api.domain.model.Veiculo;

import java.util.List;
import java.util.Optional;

public interface VeiculoRepository {

    List<VeiculoView> listarPorMarca(String codigoFipeMarca);

    Optional<Veiculo> buscarPorId(Long id);

    Veiculo atualizar(Long id, Veiculo veiculo);
}
