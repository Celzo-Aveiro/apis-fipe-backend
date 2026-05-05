package com.celzo_aveiro.fipe_api_consumer.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Veiculo")
class VeiculoTest {

    private static final Marca HONDA = Marca.of("59", "Honda");

    @Test
    @DisplayName("constrói com dados válidos")
    void constroiComDadosValidos() {
        var veiculo = new Veiculo("5940-1", HONDA, "Civic LXS 1.7");

        assertThat(veiculo.codigoFipe()).isEqualTo("5940-1");
        assertThat(veiculo.marca()).isEqualTo(HONDA);
        assertThat(veiculo.modelo()).isEqualTo("Civic LXS 1.7");
    }

    @Test
    @DisplayName("cria veículo pela factory")
    void criaPelaFactory() {
        var veiculo = Veiculo.of("5940-1", HONDA, "Civic LXS 1.7");

        assertThat(veiculo.codigoFipe()).isEqualTo("5940-1");
        assertThat(veiculo.marca()).isEqualTo(HONDA);
        assertThat(veiculo.modelo()).isEqualTo("Civic LXS 1.7");
    }

    @Test
    @DisplayName("normaliza espaços nas bordas")
    void normalizaEspacosNasBordas() {
        var veiculo = new Veiculo("  5940-1  ", HONDA, "  Civic LXS 1.7  ");

        assertThat(veiculo.codigoFipe()).isEqualTo("5940-1");
        assertThat(veiculo.modelo()).isEqualTo("Civic LXS 1.7");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t", "\n"})
    @DisplayName("rejeita código FIPE vazio ou em branco")
    void rejeitaCodigoFipeVazio(String codigo) {
        assertThatThrownBy(() -> new Veiculo(codigo, HONDA, "Civic LXS 1.7"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("codigoFipe");
    }

    @Test
    @DisplayName("rejeita marca nula")
    void rejeitaMarcaNula() {
        assertThatThrownBy(() -> new Veiculo("5940-1", null, "Civic LXS 1.7"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("marca");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t", "\n"})
    @DisplayName("rejeita modelo vazio ou em branco")
    void rejeitaModeloVazio(String modelo) {
        assertThatThrownBy(() -> new Veiculo("5940-1", HONDA, modelo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("modelo");
    }
}
