package com.celzo_aveiro.fipe_api.application.usecase.impl;

import com.celzo_aveiro.fipe_api.application.port.out.VeiculoRepository;
import com.celzo_aveiro.fipe_api.application.usecase.AtualizarVeiculo;
import com.celzo_aveiro.fipe_api.domain.exception.VeiculoNaoEncontradoException;
import com.celzo_aveiro.fipe_api.domain.model.Veiculo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class AtualizarVeiculoImpl implements AtualizarVeiculo {

    private final VeiculoRepository veiculoRepository;

    public AtualizarVeiculoImpl(VeiculoRepository veiculoRepository) {
        this.veiculoRepository = Objects.requireNonNull(veiculoRepository);
    }

    @Override
    @Transactional
    @CacheEvict(value = "veiculosPorMarca", allEntries = true)
    public Veiculo executar(Long id, String modelo, String observacoes) {
        var veiculo = veiculoRepository.buscarPorId(id)
                .orElseThrow(() -> new VeiculoNaoEncontradoException(id));
        var atualizado = veiculo.atualizar(modelo, observacoes);
        return veiculoRepository.atualizar(id, atualizado);
    }
}
