package com.celzo_aveiro.fipe_api.application.usecase.impl;

import com.celzo_aveiro.fipe_api.application.port.out.MarcaConsultaRepository;
import com.celzo_aveiro.fipe_api.application.usecase.ListarMarcas;
import com.celzo_aveiro.fipe_api.domain.model.Marca;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ListarMarcasImpl implements ListarMarcas {

    private final MarcaConsultaRepository marcaConsultaRepository;

    public ListarMarcasImpl(MarcaConsultaRepository marcaConsultaRepository) {
        this.marcaConsultaRepository = Objects.requireNonNull(marcaConsultaRepository);
    }

    @Override
    public List<Marca> listar() {
        return marcaConsultaRepository.listarTodas();
    }
}
