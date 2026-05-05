package com.celzo_aveiro.fipe_api.domain.exception;

public class MarcaNaoEncontradaException extends RuntimeException {

    private final String codigoFipe;

    public MarcaNaoEncontradaException(String codigoFipe) {
        super("marca com codigoFipe '" + codigoFipe + "' nao encontrada");
        this.codigoFipe = codigoFipe;
    }

    public String codigoFipe() {
        return codigoFipe;
    }
}
