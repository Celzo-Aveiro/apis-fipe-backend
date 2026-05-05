package com.celzo_aveiro.fipe_api_consumer.interfaces.messaging;

import com.celzo_aveiro.fipe_api_consumer.application.usecase.ImportarVeiculosDaMarca;
import com.celzo_aveiro.fipe_api_consumer.infrastructure.messaging.MarcaMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class MarcaListener {

    private final ImportarVeiculosDaMarca importarVeiculosDaMarca;

    public MarcaListener(ImportarVeiculosDaMarca importarVeiculosDaMarca) {
        this.importarVeiculosDaMarca = Objects.requireNonNull(importarVeiculosDaMarca);
    }

    @RabbitListener(queues = "${fipe.messaging.marcas-queue}")
    public void onMarca(MarcaMessage message) {
        importarVeiculosDaMarca.importar(message.toDomain());
    }
}
