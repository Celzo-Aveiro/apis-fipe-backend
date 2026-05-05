package com.celzo_aveiro.fipe_api_consumer.infrastructure.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    private final String exchange;
    private final String marcasQueue;
    private final String marcasRoutingKey;

    public RabbitMqConfig(
            @Value("${fipe.messaging.exchange}") String exchange,
            @Value("${fipe.messaging.marcas-queue}") String marcasQueue,
            @Value("${fipe.messaging.marcas-routing-key}") String marcasRoutingKey
    ) {
        this.exchange = exchange;
        this.marcasQueue = marcasQueue;
        this.marcasRoutingKey = marcasRoutingKey;
    }

    @Bean
    DirectExchange fipeExchange() {
        return new DirectExchange(exchange, true, false);
    }

    @Bean
    Queue marcasQueue() {
        return new Queue(marcasQueue, true);
    }

    @Bean
    Binding marcasBinding(Queue marcasQueue, DirectExchange fipeExchange) {
        return BindingBuilder.bind(marcasQueue).to(fipeExchange).with(marcasRoutingKey);
    }

    @Bean
    MessageConverter jacksonMessageConverter() {
        var converter = new Jackson2JsonMessageConverter();
        var typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.setTrustedPackages("*");
        typeMapper.setTypePrecedence(DefaultJackson2JavaTypeMapper.TypePrecedence.INFERRED);
        converter.setJavaTypeMapper(typeMapper);
        return converter;
    }
}
