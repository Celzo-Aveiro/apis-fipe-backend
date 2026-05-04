package com.celzo_aveiro.fipe_api.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Veiculo")
class VeiculoTest {

    private static final Marca HONDA = new Marca("59", "Honda");

    @Test
    @DisplayName("constrói com dados válidos")
    void constroiComDadosValidos() {
        var veiculo = new Veiculo("5940-1", HONDA, "Civic LXS 1.7", "manual");

        assertThat(veiculo.codigoFipe()).isEqualTo("5940-1");
        assertThat(veiculo.marca()).isEqualTo(HONDA);
        assertThat(veiculo.modelo()).isEqualTo("Civic LXS 1.7");
        assertThat(veiculo.observacoes()).isEqualTo("manual");
    }

    @Test
    @DisplayName("aceita observações nulas")
    void aceitaObservacoesNulas() {
        var veiculo = new Veiculo("5940-1", HONDA, "Civic LXS 1.7", null);

        assertThat(veiculo.observacoes()).isNull();
    }

    @Test
    @DisplayName("normaliza observações em branco para nulo")
    void normalizaObservacoesEmBrancoParaNulo() {
        var veiculo = new Veiculo("5940-1", HONDA, "Civic LXS 1.7", "   ");

        assertThat(veiculo.observacoes()).isNull();
    }

    @Test
    @DisplayName("normaliza espaços nas bordas")
    void normalizaEspacosNasBordas() {
        var veiculo = new Veiculo("  5940-1  ", HONDA, "  Civic LXS 1.7  ", "  manual  ");

        assertThat(veiculo.codigoFipe()).isEqualTo("5940-1");
        assertThat(veiculo.modelo()).isEqualTo("Civic LXS 1.7");
        assertThat(veiculo.observacoes()).isEqualTo("manual");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t", "\n"})
    @DisplayName("rejeita código FIPE vazio ou em branco")
    void rejeitaCodigoFipeVazio(String codigo) {
        assertThatThrownBy(() -> new Veiculo(codigo, HONDA, "Civic LXS 1.7", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("codigoFipe");
    }

    @Test
    @DisplayName("rejeita marca nula")
    void rejeitaMarcaNula() {
        assertThatThrownBy(() -> new Veiculo("5940-1", null, "Civic LXS 1.7", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("marca");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t", "\n"})
    @DisplayName("rejeita modelo vazio ou em branco")
    void rejeitaModeloVazio(String modelo) {
        assertThatThrownBy(() -> new Veiculo("5940-1", HONDA, modelo, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("modelo");
    }

    @Test
    @DisplayName("atualizar gera nova instância com modelo e observações novos")
    void atualizarGeraNovaInstancia() {
        var original = new Veiculo("5940-1", HONDA, "Civic LXS 1.7", "manual");

        var atualizado = original.atualizar("Civic EXS 1.8", "automático, único dono");

        assertThat(atualizado).isNotSameAs(original);
        assertThat(atualizado.codigoFipe()).isEqualTo(original.codigoFipe());
        assertThat(atualizado.marca()).isEqualTo(original.marca());
        assertThat(atualizado.modelo()).isEqualTo("Civic EXS 1.8");
        assertThat(atualizado.observacoes()).isEqualTo("automático, único dono");
    }

    @Test
    @DisplayName("atualizar valida invariantes do modelo")
    void atualizarValidaModelo() {
        var original = new Veiculo("5940-1", HONDA, "Civic LXS 1.7", null);

        assertThatThrownBy(() -> original.atualizar("   ", "qualquer"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("modelo");
    }
}
