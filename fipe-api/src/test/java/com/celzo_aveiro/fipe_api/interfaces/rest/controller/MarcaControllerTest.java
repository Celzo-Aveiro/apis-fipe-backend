package com.celzo_aveiro.fipe_api.interfaces.rest.controller;

import com.celzo_aveiro.fipe_api.application.dto.VeiculoView;
import com.celzo_aveiro.fipe_api.application.usecase.ListarMarcas;
import com.celzo_aveiro.fipe_api.application.usecase.ListarVeiculosPorMarca;
import com.celzo_aveiro.fipe_api.domain.exception.MarcaNaoEncontradaException;
import com.celzo_aveiro.fipe_api.domain.model.Marca;
import com.celzo_aveiro.fipe_api.interfaces.rest.handler.GlobalExceptionHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("MarcaController")
class MarcaControllerTest {

    private final ListarMarcas listarMarcas = mock(ListarMarcas.class);
    private final ListarVeiculosPorMarca listarVeiculosPorMarca = mock(ListarVeiculosPorMarca.class);
    private final MockMvc mockMvc = MockMvcBuilders
            .standaloneSetup(new MarcaController(listarMarcas, listarVeiculosPorMarca))
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();

    @Test
    @DisplayName("GET /api/v1/marcas retorna 200 com lista de marcas")
    void listaMarcas() throws Exception {
        when(listarMarcas.listar()).thenReturn(List.of(
                Marca.of("59", "Honda"),
                Marca.of("56", "Toyota")
        ));

        mockMvc.perform(get("/api/v1/marcas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].codigoFipe", is("59")))
                .andExpect(jsonPath("$[0].nome", is("Honda")))
                .andExpect(jsonPath("$[1].codigoFipe", is("56")))
                .andExpect(jsonPath("$[1].nome", is("Toyota")));
    }

    @Test
    @DisplayName("GET /api/v1/marcas/{codigo}/veiculos retorna 200 com lista")
    void listaVeiculosDaMarca() throws Exception {
        when(listarVeiculosPorMarca.listar("59")).thenReturn(List.of(
                new VeiculoView(1L, "5940-1", "Civic LXS 1.7", null),
                new VeiculoView(2L, "5901-1", "Fit LX 1.4", "manual")
        ));

        mockMvc.perform(get("/api/v1/marcas/59/veiculos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].codigoFipe", is("5940-1")))
                .andExpect(jsonPath("$[0].modelo", is("Civic LXS 1.7")))
                .andExpect(jsonPath("$[1].observacoes", is("manual")));
    }

    @Test
    @DisplayName("GET /api/v1/marcas/{codigo}/veiculos retorna 404 ProblemDetail quando marca não existe")
    void retorna404QuandoMarcaNaoExiste() throws Exception {
        when(listarVeiculosPorMarca.listar("999"))
                .thenThrow(new MarcaNaoEncontradaException("999"));

        mockMvc.perform(get("/api/v1/marcas/999/veiculos"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.title", is("Marca não encontrada")))
                .andExpect(jsonPath("$.codigoFipe", is("999")));
    }
}
