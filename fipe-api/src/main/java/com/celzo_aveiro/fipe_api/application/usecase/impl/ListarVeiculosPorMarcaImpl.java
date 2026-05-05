package com.celzo_aveiro.fipe_api.application.usecase.impl;

import com.celzo_aveiro.fipe_api.application.dto.VeiculoView;
import com.celzo_aveiro.fipe_api.application.port.out.MarcaConsultaRepository;
import com.celzo_aveiro.fipe_api.application.port.out.VeiculoRepository;
import com.celzo_aveiro.fipe_api.application.usecase.ListarVeiculosPorMarca;
import com.celzo_aveiro.fipe_api.domain.exception.MarcaNaoEncontradaException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ListarVeiculosPorMarcaImpl implements ListarVeiculosPorMarca {

    private final MarcaConsultaRepository marcaConsultaRepository;
    private final VeiculoRepository veiculoRepository;

    public ListarVeiculosPorMarcaImpl(
            MarcaConsultaRepository marcaConsultaRepository,
            VeiculoRepository veiculoRepository
    ) {
        this.marcaConsultaRepository = Objects.requireNonNull(marcaConsultaRepository);
        this.veiculoRepository = Objects.requireNonNull(veiculoRepository);
    }

    @Override
    @Cacheable(value = "veiculosPorMarca", key = "#codigoFipeMarca")
    public List<VeiculoView> listar(String codigoFipeMarca) {
        if (!marcaConsultaRepository.existePorCodigoFipe(codigoFipeMarca)) {
            throw new MarcaNaoEncontradaException(codigoFipeMarca);
        }
        return veiculoRepository.listarPorMarca(codigoFipeMarca);
    }
}
