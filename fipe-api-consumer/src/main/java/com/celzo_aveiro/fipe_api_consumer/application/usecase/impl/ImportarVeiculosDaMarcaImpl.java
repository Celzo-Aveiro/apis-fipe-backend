package com.celzo_aveiro.fipe_api_consumer.application.usecase.impl;

import com.celzo_aveiro.fipe_api_consumer.application.port.out.FipeVeiculoClient;
import com.celzo_aveiro.fipe_api_consumer.application.port.out.MarcaRepository;
import com.celzo_aveiro.fipe_api_consumer.application.port.out.VeiculoRepository;
import com.celzo_aveiro.fipe_api_consumer.application.usecase.ImportarVeiculosDaMarca;
import com.celzo_aveiro.fipe_api_consumer.domain.model.Marca;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class ImportarVeiculosDaMarcaImpl implements ImportarVeiculosDaMarca {

    private final FipeVeiculoClient fipeVeiculoClient;
    private final MarcaRepository marcaRepository;
    private final VeiculoRepository veiculoRepository;

    public ImportarVeiculosDaMarcaImpl(
            FipeVeiculoClient fipeVeiculoClient,
            MarcaRepository marcaRepository,
            VeiculoRepository veiculoRepository
    ) {
        this.fipeVeiculoClient = Objects.requireNonNull(fipeVeiculoClient);
        this.marcaRepository = Objects.requireNonNull(marcaRepository);
        this.veiculoRepository = Objects.requireNonNull(veiculoRepository);
    }

    @Override
    @Transactional
    public void importar(Marca marca) {
        var marcaPersistida = marcaRepository.salvar(marca);
        var veiculos = fipeVeiculoClient.buscarVeiculosDaMarca(marcaPersistida);
        veiculoRepository.salvarTodos(veiculos);
    }
}
