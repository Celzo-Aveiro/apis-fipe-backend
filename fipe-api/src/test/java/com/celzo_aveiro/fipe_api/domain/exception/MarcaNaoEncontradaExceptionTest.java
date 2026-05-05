package com.celzo_aveiro.fipe_api.domain.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("MarcaNaoEncontradaException")
class MarcaNaoEncontradaExceptionTest {

    @Test
    @DisplayName("expõe codigoFipe pesquisado")
    void expoeCodigoFipePesquisado() {
        var ex = new MarcaNaoEncontradaException("59");

        assertThat(ex.codigoFipe()).isEqualTo("59");
    }

    @Test
    @DisplayName("inclui codigoFipe na mensagem")
    void incluiCodigoFipeNaMensagem() {
        var ex = new MarcaNaoEncontradaException("59");

        assertThat(ex.getMessage()).contains("59");
    }
}
