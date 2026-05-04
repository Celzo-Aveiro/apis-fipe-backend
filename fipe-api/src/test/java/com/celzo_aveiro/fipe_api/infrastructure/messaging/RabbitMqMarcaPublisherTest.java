package com.celzo_aveiro.fipe_api.infrastructure.messaging;

import com.celzo_aveiro.fipe_api.domain.model.Marca;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("RabbitMqMarcaPublisher")
class RabbitMqMarcaPublisherTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Test
    @DisplayName("publica marca no exchange e routing key configurados")
    void publicaMarcaNoExchangeERoutingKeyConfigurados() {
        var publisher = new RabbitMqMarcaPublisher(
                rabbitTemplate,
                "fipe.exchange",
                "fipe.marcas"
        );
        var marca = Marca.of("59", "Honda");
        var payloadCaptor = ArgumentCaptor.forClass(MarcaMessage.class);

        publisher.publicar(marca);

        verify(rabbitTemplate).convertAndSend(
                "fipe.exchange",
                "fipe.marcas",
                payloadCaptor.capture()
        );
        assertThat(payloadCaptor.getValue())
                .isEqualTo(new MarcaMessage("59", "Honda"));
    }
}
