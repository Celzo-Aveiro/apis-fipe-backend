package com.celzo_aveiro.fipe_api_consumer.infrastructure.client;

import com.celzo_aveiro.fipe_api_consumer.application.port.out.FipeVeiculoClient;
import com.celzo_aveiro.fipe_api_consumer.domain.model.Marca;
import com.celzo_aveiro.fipe_api_consumer.domain.model.Veiculo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class FipeVeiculoHttpClient implements FipeVeiculoClient {

    private final RestClient restClient;

    public FipeVeiculoHttpClient(
            RestClient.Builder restClientBuilder,
            @Value("${fipe.client.base-url}") String baseUrl
    ) {
        this.restClient = restClientBuilder.baseUrl(baseUrl).build();
    }

    @Override
    public List<Veiculo> buscarVeiculosDaMarca(Marca marca) {
        var resposta = restClient.get()
                .uri("/carros/marcas/{codigo}/modelos", marca.codigoFipe())
                .retrieve()
                .body(ModelosFipeResponse.class);

        if (resposta == null || resposta.modelos() == null) {
            return List.of();
        }

        return resposta.modelos().stream()
                .map(modelo -> Veiculo.of(modelo.codigo(), marca, modelo.nome()))
                .toList();
    }

    record ModelosFipeResponse(List<ModeloFipe> modelos) {

        record ModeloFipe(String codigo, String nome) {
        }
    }
}
