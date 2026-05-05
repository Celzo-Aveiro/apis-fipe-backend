package com.celzo_aveiro.fipe_api_consumer.infrastructure.client;

import com.celzo_aveiro.fipe_api_consumer.domain.model.Marca;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@DisplayName("FipeVeiculoHttpClient")
class FipeVeiculoHttpClientTest {

    private static final String BASE_URL = "https://parallelum.com.br/fipe/api/v1";
    private static final Marca HONDA = Marca.of("59", "Honda");

    @Test
    @DisplayName("busca modelos da marca na FIPE e converte para domínio")
    void buscaModelosDaMarcaNaFipeEConverteParaDominio() {
        var builder = RestClient.builder();
        var server = MockRestServiceServer.bindTo(builder).build();
        var client = new FipeVeiculoHttpClient(builder, BASE_URL);
        server.expect(requestTo(BASE_URL + "/carros/marcas/59/modelos"))
                .andExpect(method(GET))
                .andRespond(withSuccess("""
                        {
                          "modelos": [
                            {"codigo": "5940", "nome": "Civic LXS 1.7"},
                            {"codigo": "5901", "nome": "Fit LX 1.4"}
                          ],
                          "anos": []
                        }
                        """, MediaType.APPLICATION_JSON));

        var veiculos = client.buscarVeiculosDaMarca(HONDA);

        assertThat(veiculos)
                .extracting("codigoFipe", "modelo")
                .containsExactly(
                        org.assertj.core.groups.Tuple.tuple("5940", "Civic LXS 1.7"),
                        org.assertj.core.groups.Tuple.tuple("5901", "Fit LX 1.4")
                );
        assertThat(veiculos).allSatisfy(v -> assertThat(v.marca()).isEqualTo(HONDA));
        server.verify();
    }

    @Test
    @DisplayName("retorna lista vazia quando a FIPE não traz modelos")
    void retornaListaVaziaQuandoFipeNaoTrazModelos() {
        var builder = RestClient.builder();
        var server = MockRestServiceServer.bindTo(builder).build();
        var client = new FipeVeiculoHttpClient(builder, BASE_URL);
        server.expect(requestTo(BASE_URL + "/carros/marcas/59/modelos"))
                .andExpect(method(GET))
                .andRespond(withSuccess("""
                        {"modelos": [], "anos": []}
                        """, MediaType.APPLICATION_JSON));

        var veiculos = client.buscarVeiculosDaMarca(HONDA);

        assertThat(veiculos).isEmpty();
        server.verify();
    }
}
