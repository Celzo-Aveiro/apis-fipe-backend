package com.celzo_aveiro.fipe_api.interfaces.rest.controller;

import com.celzo_aveiro.fipe_api.application.usecase.ListarMarcas;
import com.celzo_aveiro.fipe_api.application.usecase.ListarVeiculosPorMarca;
import com.celzo_aveiro.fipe_api.interfaces.rest.dto.MarcaResponse;
import com.celzo_aveiro.fipe_api.interfaces.rest.dto.VeiculoResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/marcas")
public class MarcaController {

    private final ListarMarcas listarMarcas;
    private final ListarVeiculosPorMarca listarVeiculosPorMarca;

    public MarcaController(
            ListarMarcas listarMarcas,
            ListarVeiculosPorMarca listarVeiculosPorMarca
    ) {
        this.listarMarcas = listarMarcas;
        this.listarVeiculosPorMarca = listarVeiculosPorMarca;
    }

    @GetMapping
    public List<MarcaResponse> listar() {
        return listarMarcas.listar().stream()
                .map(MarcaResponse::from)
                .toList();
    }

    @GetMapping("/{codigoFipe}/veiculos")
    public List<VeiculoResponse> listarVeiculos(@PathVariable String codigoFipe) {
        return listarVeiculosPorMarca.listar(codigoFipe).stream()
                .map(VeiculoResponse::from)
                .toList();
    }
}
