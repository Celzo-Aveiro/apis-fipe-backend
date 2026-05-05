package com.celzo_aveiro.fipe_api.infrastructure.messaging;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("RabbitMqConfig")
class RabbitMqConfigTest {

    private final RabbitMqConfig config = new RabbitMqConfig(
            "fipe.exchange",
            "fipe.marcas.queue",
            "fipe.marcas"
    );

    @Test
    @DisplayName("declara exchange de marcas")
    void declaraExchangeDeMarcas() {
        var exchange = config.fipeExchange();

        assertThat(exchange.getName()).isEqualTo("fipe.exchange");
        assertThat(exchange.isDurable()).isTrue();
    }

    @Test
    @DisplayName("declara fila de marcas")
    void declaraFilaDeMarcas() {
        var queue = config.marcasQueue();

        assertThat(queue.getName()).isEqualTo("fipe.marcas.queue");
        assertThat(queue.isDurable()).isTrue();
    }

    @Test
    @DisplayName("vincula fila ao exchange com routing key de marcas")
    void vinculaFilaAoExchangeComRoutingKeyDeMarcas() {
        var binding = config.marcasBinding(
                config.marcasQueue(),
                config.fipeExchange()
        );

        assertThat(binding.getExchange()).isEqualTo("fipe.exchange");
        assertThat(binding.getDestination()).isEqualTo("fipe.marcas.queue");
        assertThat(binding.getRoutingKey()).isEqualTo("fipe.marcas");
    }
}
