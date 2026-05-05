package com.celzo_aveiro.fipe_api.interfaces.rest.controller;

import com.celzo_aveiro.fipe_api.application.dto.CargaInicialResponse;
import com.celzo_aveiro.fipe_api.application.usecase.IniciarCargaUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("CargaInicialController")
class CargaInicialControllerTest {

    private final IniciarCargaUseCase iniciarCargaUseCase = mock(IniciarCargaUseCase.class);
    private final MockMvc mockMvc = MockMvcBuilders
            .standaloneSetup(new CargaInicialController(iniciarCargaUseCase))
            .build();

    @Test
    @DisplayName("retorna 202 com resumo da carga iniciada")
    void retornaAcceptedComResumoDaCargaIniciada() throws Exception {
        var cargaId = UUID.fromString("8e11356b-8241-4ce7-a37c-a438c3447787");
        var iniciadoEm = Instant.parse("2026-05-04T22:10:00Z");
        when(iniciarCargaUseCase.iniciar())
                .thenReturn(new CargaInicialResponse(cargaId, iniciadoEm, 2));

        mockMvc.perform(post("/api/v1/carga-inicial"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.cargaId", is(cargaId.toString())))
                .andExpect(jsonPath("$.iniciadoEm", is("2026-05-04T22:10:00Z")))
                .andExpect(jsonPath("$.marcasEncontradas", is(2)));

        verify(iniciarCargaUseCase).iniciar();
    }
}
