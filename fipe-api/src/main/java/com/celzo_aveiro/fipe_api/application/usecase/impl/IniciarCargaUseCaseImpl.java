package com.celzo_aveiro.fipe_api.application.usecase.impl;

import com.celzo_aveiro.fipe_api.application.dto.CargaInicialResponse;
import com.celzo_aveiro.fipe_api.application.port.out.FipeMarcaClient;
import com.celzo_aveiro.fipe_api.application.port.out.MarcaMessagePublisher;
import com.celzo_aveiro.fipe_api.application.usecase.IniciarCargaUseCase;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Service
public class IniciarCargaUseCaseImpl implements IniciarCargaUseCase {

    private final FipeMarcaClient fipeMarcaClient;
    private final MarcaMessagePublisher marcaMessagePublisher;
    private final Clock clock;

    public IniciarCargaUseCaseImpl(
            FipeMarcaClient fipeMarcaClient,
            MarcaMessagePublisher marcaMessagePublisher
    ) {
        this(fipeMarcaClient, marcaMessagePublisher, Clock.systemUTC());
    }

    IniciarCargaUseCaseImpl(
            FipeMarcaClient fipeMarcaClient,
            MarcaMessagePublisher marcaMessagePublisher,
            Clock clock
    ) {
        this.fipeMarcaClient = Objects.requireNonNull(fipeMarcaClient);
        this.marcaMessagePublisher = Objects.requireNonNull(marcaMessagePublisher);
        this.clock = Objects.requireNonNull(clock);
    }

    @Override
    public CargaInicialResponse iniciar() {
        var marcas = fipeMarcaClient.buscarMarcas();

        marcas.forEach(marcaMessagePublisher::publicar);

        return new CargaInicialResponse(
                UUID.randomUUID(),
                Instant.now(clock),
                marcas.size()
        );
    }
}
