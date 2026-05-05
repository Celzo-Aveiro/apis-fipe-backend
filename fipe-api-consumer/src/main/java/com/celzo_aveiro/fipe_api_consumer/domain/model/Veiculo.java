package com.celzo_aveiro.fipe_api_consumer.domain.model;

public record Veiculo(String codigoFipe, Marca marca, String modelo) {

    public Veiculo {
        if (codigoFipe == null || codigoFipe.isBlank()) {
            throw new IllegalArgumentException("codigoFipe não pode ser vazio");
        }
        if (marca == null) {
            throw new IllegalArgumentException("marca é obrigatória");
        }
        if (modelo == null || modelo.isBlank()) {
            throw new IllegalArgumentException("modelo não pode ser vazio");
        }
        codigoFipe = codigoFipe.trim();
        modelo = modelo.trim();
    }

    public static Veiculo of(String codigoFipe, Marca marca, String modelo) {
        return new Veiculo(codigoFipe, marca, modelo);
    }
}
