package com.celzo_aveiro.fipe_api.infrastructure.messaging;

import com.celzo_aveiro.fipe_api.application.port.out.MarcaMessagePublisher;
import com.celzo_aveiro.fipe_api.domain.model.Marca;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class RabbitMqMarcaPublisher implements MarcaMessagePublisher {

    private final RabbitTemplate rabbitTemplate;
    private final String exchange;
    private final String routingKey;

    public RabbitMqMarcaPublisher(
            RabbitTemplate rabbitTemplate,
            @Value("${fipe.messaging.exchange}") String exchange,
            @Value("${fipe.messaging.marcas-routing-key}") String routingKey
    ) {
        this.rabbitTemplate = Objects.requireNonNull(rabbitTemplate);
        this.exchange = Objects.requireNonNull(exchange);
        this.routingKey = Objects.requireNonNull(routingKey);
    }

    @Override
    public void publicar(Marca marca) {
        rabbitTemplate.convertAndSend(exchange, routingKey, MarcaMessage.from(marca));
    }
}
