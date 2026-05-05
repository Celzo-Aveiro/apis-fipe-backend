package com.celzo_aveiro.fipe_api.interfaces.rest.controller;

import com.celzo_aveiro.fipe_api.application.usecase.AtualizarVeiculo;
import com.celzo_aveiro.fipe_api.domain.exception.VeiculoNaoEncontradoException;
import com.celzo_aveiro.fipe_api.domain.model.Marca;
import com.celzo_aveiro.fipe_api.domain.model.Veiculo;
import com.celzo_aveiro.fipe_api.interfaces.rest.handler.GlobalExceptionHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("VeiculoController")
class VeiculoControllerTest {

    private final AtualizarVeiculo atualizarVeiculo = mock(AtualizarVeiculo.class);
    private final MockMvc mockMvc = MockMvcBuilders
            .standaloneSetup(new VeiculoController(atualizarVeiculo))
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();

    @Test
    @DisplayName("PUT /api/v1/veiculos/{id} retorna 200 com veículo atualizado")
    void atualizaVeiculo() throws Exception {
        var honda = Marca.of("59", "Honda");
        var atualizado = new Veiculo("5940-1", honda, "Civic EXS 1.8", "automático");
        when(atualizarVeiculo.executar(7L, "Civic EXS 1.8", "automático"))
                .thenReturn(atualizado);

        mockMvc.perform(put("/api/v1/veiculos/{id}", 7L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"modelo": "Civic EXS 1.8", "observacoes": "automático"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(7)))
                .andExpect(jsonPath("$.codigoFipe", is("5940-1")))
                .andExpect(jsonPath("$.modelo", is("Civic EXS 1.8")))
                .andExpect(jsonPath("$.observacoes", is("automático")));
    }

    @Test
    @DisplayName("PUT /api/v1/veiculos/{id} retorna 400 quando modelo está em branco")
    void retorna400ComModeloVazio() throws Exception {
        mockMvc.perform(put("/api/v1/veiculos/{id}", 7L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"modelo": "", "observacoes": null}
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /api/v1/veiculos/{id} retorna 404 ProblemDetail quando id não existe")
    void retorna404QuandoIdNaoExiste() throws Exception {
        when(atualizarVeiculo.executar(any(), any(), any()))
                .thenThrow(new VeiculoNaoEncontradoException(99L));

        mockMvc.perform(put("/api/v1/veiculos/{id}", 99L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"modelo": "qualquer"}
                                """))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.title", is("Veículo não encontrado")))
                .andExpect(jsonPath("$.id", is(99)));
    }
}
