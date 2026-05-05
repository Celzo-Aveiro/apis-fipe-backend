package com.celzo_aveiro.fipe_api.infrastructure.client;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.http.HttpMethod.GET;

@DisplayName("FipeMarcaHttpClient")
class FipeMarcaHttpClientTest {

    @Test
    @DisplayName("busca marcas de carro na FIPE e converte para dominio")
    void buscaMarcasDeCarroNaFipeEConverteParaDominio() {
        var builder = RestClient.builder();
        var server = MockRestServiceServer.bindTo(builder).build();
        var client = new FipeMarcaHttpClient(
                builder,
                "https://parallelum.com.br/fipe/api/v1"
        );
        server.expect(requestTo("https://parallelum.com.br/fipe/api/v1/carros/marcas"))
                .andExpect(method(GET))
                .andRespond(withSuccess("""
                        [
                          {"codigo": "59", "nome": "Honda"},
                          {"codigo": "56", "nome": "Toyota"}
                        ]
                        """, MediaType.APPLICATION_JSON));

        var marcas = client.buscarMarcas();

        assertThat(marcas)
                .extracting("codigoFipe", "nome")
                .containsExactly(
                        org.assertj.core.groups.Tuple.tuple("59", "Honda"),
                        org.assertj.core.groups.Tuple.tuple("56", "Toyota")
                );
        server.verify();
    }

    @Test
    @DisplayName("retorna lista vazia quando a FIPE responde sem corpo")
    void retornaListaVaziaQuandoFipeRespondeSemCorpo() {
        var builder = RestClient.builder();
        var server = MockRestServiceServer.bindTo(builder).build();
        var client = new FipeMarcaHttpClient(
                builder,
                "https://parallelum.com.br/fipe/api/v1"
        );
        server.expect(requestTo("https://parallelum.com.br/fipe/api/v1/carros/marcas"))
                .andExpect(method(GET))
                .andRespond(withSuccess());

        var marcas = client.buscarMarcas();

        assertThat(marcas).isEmpty();
        server.verify();
    }
}
