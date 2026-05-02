/**
 * fipe-api (API-1) — orquestrador REST do catálogo FIPE.
 *
 * <p>Aciona a carga inicial, publica marcas em fila (RabbitMQ) para a API-2
 * processar, e expõe consultas/atualizações do catálogo persistido.</p>
 *
 * <p>Organizada em arquitetura hexagonal (ports &amp; adapters):</p>
 * <ul>
 *   <li>{@code domain} — modelo e regras de negócio puras</li>
 *   <li>{@code application} — casos de uso e contratos (portas)</li>
 *   <li>{@code infrastructure} — adaptadores de saída e configuração</li>
 *   <li>{@code interfaces} — adaptadores de entrada (REST)</li>
 * </ul>
 */
package com.celzo_aveiro.fipe_api;
