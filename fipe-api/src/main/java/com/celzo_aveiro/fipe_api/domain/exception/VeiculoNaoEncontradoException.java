package com.celzo_aveiro.fipe_api.domain.exception;

public class VeiculoNaoEncontradoException extends RuntimeException {

    private final Long id;

    public VeiculoNaoEncontradoException(Long id) {
        super("veiculo com id " + id + " nao encontrado");
        this.id = id;
    }

    public Long id() {
        return id;
    }
}
