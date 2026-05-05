package com.celzo_aveiro.fipe_api.interfaces.rest.controller;

import com.celzo_aveiro.fipe_api.application.usecase.AtualizarVeiculo;
import com.celzo_aveiro.fipe_api.interfaces.rest.dto.AtualizarVeiculoRequest;
import com.celzo_aveiro.fipe_api.interfaces.rest.dto.VeiculoResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/veiculos")
public class VeiculoController {

    private final AtualizarVeiculo atualizarVeiculo;

    public VeiculoController(AtualizarVeiculo atualizarVeiculo) {
        this.atualizarVeiculo = atualizarVeiculo;
    }

    @PutMapping("/{id}")
    public VeiculoResponse atualizar(
            @PathVariable Long id,
            @RequestBody @Valid AtualizarVeiculoRequest request
    ) {
        var veiculo = atualizarVeiculo.executar(id, request.modelo(), request.observacoes());
        return new VeiculoResponse(id, veiculo.codigoFipe(), veiculo.modelo(), veiculo.observacoes());
    }
}
