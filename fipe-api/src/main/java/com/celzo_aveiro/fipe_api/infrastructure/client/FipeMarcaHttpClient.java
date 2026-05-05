package com.celzo_aveiro.fipe_api.infrastructure.client;

import com.celzo_aveiro.fipe_api.application.port.out.FipeMarcaClient;
import com.celzo_aveiro.fipe_api.domain.model.Marca;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

@Component
public class FipeMarcaHttpClient implements FipeMarcaClient {

    private final RestClient restClient;

    public FipeMarcaHttpClient(
            RestClient.Builder restClientBuilder,
            @Value("${fipe.client.base-url}") String baseUrl
    ) {
        this.restClient = restClientBuilder.baseUrl(baseUrl).build();
    }

    @Override
    public List<Marca> buscarMarcas() {
        var response = restClient.get()
                .uri("/carros/marcas")
                .retrieve()
                .body(MarcaFipeResponse[].class);

        if (response == null) {
            return List.of();
        }

        return Arrays.stream(response)
                .map(MarcaFipeResponse::toDomain)
                .toList();
    }

    record MarcaFipeResponse(String codigo, String nome) {

        Marca toDomain() {
            return Marca.of(codigo, nome);
        }
    }
}
