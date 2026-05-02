/**
 * fipe-api-consumer (API-2) — consumidor da fila de marcas.
 *
 * <p>Recebe marcas via RabbitMQ, busca códigos e modelos no serviço FIPE
 * e persiste o catálogo no banco SQL. É a fronteira que escreve no schema
 * (dona das migrations Flyway).</p>
 *
 * <p>Organizada em arquitetura hexagonal (ports &amp; adapters):</p>
 * <ul>
 *   <li>{@code domain} — modelo e regras de negócio puras</li>
 *   <li>{@code application} — casos de uso e contratos (portas)</li>
 *   <li>{@code infrastructure} — adaptadores de saída e configuração</li>
 *   <li>{@code interfaces} — adaptadores de entrada (listener AMQP)</li>
 * </ul>
 */
package com.celzo_aveiro.fipe_api_consumer;
