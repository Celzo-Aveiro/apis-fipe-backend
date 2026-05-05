package com.celzo_aveiro.fipe_api.domain.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("VeiculoNaoEncontradoException")
class VeiculoNaoEncontradoExceptionTest {

    @Test
    @DisplayName("expõe id pesquisado")
    void expoeIdPesquisado() {
        var ex = new VeiculoNaoEncontradoException(42L);

        assertThat(ex.id()).isEqualTo(42L);
    }

    @Test
    @DisplayName("inclui id na mensagem")
    void incluiIdNaMensagem() {
        var ex = new VeiculoNaoEncontradoException(42L);

        assertThat(ex.getMessage()).contains("42");
    }
}
