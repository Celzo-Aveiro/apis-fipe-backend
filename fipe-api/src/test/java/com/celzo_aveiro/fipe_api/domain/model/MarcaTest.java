package com.celzo_aveiro.fipe_api.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Marca")
class MarcaTest {

    @Test
    @DisplayName("constrói com código FIPE e nome válidos")
    void constroiComDadosValidos() {
        var marca = new Marca("59", "Honda");

        assertThat(marca.codigoFipe()).isEqualTo("59");
        assertThat(marca.nome()).isEqualTo("Honda");
    }

    @Test
    @DisplayName("cria marca pela factory")
    void criaPelaFactory() {
        var marca = Marca.of("59", "Honda");

        assertThat(marca.codigoFipe()).isEqualTo("59");
        assertThat(marca.nome()).isEqualTo("Honda");
    }

    @Test
    @DisplayName("normaliza espaços nas bordas")
    void normalizaEspacosNasBordas() {
        var marca = new Marca("  59  ", "  Honda  ");

        assertThat(marca.codigoFipe()).isEqualTo("59");
        assertThat(marca.nome()).isEqualTo("Honda");
    }

    @Test
    @DisplayName("considera marcas com mesmo código FIPE como iguais")
    void consideraMesmoCodigoFipeComoIgual() {
        var honda = new Marca("59", "Honda");
        var hondaComOutroNome = new Marca("59", "Honda Automoveis");

        assertThat(honda)
                .isEqualTo(hondaComOutroNome)
                .hasSameHashCodeAs(hondaComOutroNome);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t", "\n"})
    @DisplayName("rejeita código FIPE vazio ou em branco")
    void rejeitaCodigoFipeVazio(String codigo) {
        assertThatThrownBy(() -> new Marca(codigo, "Honda"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("codigoFipe");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t", "\n"})
    @DisplayName("rejeita nome vazio ou em branco")
    void rejeitaNomeVazio(String nome) {
        assertThatThrownBy(() -> new Marca("59", nome))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("nome");
    }
}
