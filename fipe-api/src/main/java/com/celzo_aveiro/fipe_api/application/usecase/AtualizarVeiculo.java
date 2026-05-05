package com.celzo_aveiro.fipe_api.application.usecase;

import com.celzo_aveiro.fipe_api.domain.model.Veiculo;

public interface AtualizarVeiculo {

    Veiculo executar(Long id, String modelo, String observacoes);
}
