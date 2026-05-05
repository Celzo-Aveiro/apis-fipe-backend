package com.celzo_aveiro.fipe_api.interfaces.rest.controller;

import com.celzo_aveiro.fipe_api.application.dto.CargaInicialResponse;
import com.celzo_aveiro.fipe_api.application.usecase.IniciarCargaUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/carga-inicial")
public class CargaInicialController {

    private final IniciarCargaUseCase iniciarCargaUseCase;

    public CargaInicialController(IniciarCargaUseCase iniciarCargaUseCase) {
        this.iniciarCargaUseCase = iniciarCargaUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CargaInicialResponse iniciar() {
        return iniciarCargaUseCase.iniciar();
    }
}
