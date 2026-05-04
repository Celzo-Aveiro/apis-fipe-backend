package com.celzo_aveiro.fipe_api.domain.model;

/**
 * Marca de veículo no catálogo FIPE.
 *
 * <p>Identidade externa pelo {@code codigoFipe}. Imutável; rejeita valores
 * vazios e normaliza espaços nas bordas.</p>
 */
public record Marca(String codigoFipe, String nome) {

    public Marca {
        if (codigoFipe == null || codigoFipe.isBlank()) {
            throw new IllegalArgumentException("codigoFipe não pode ser vazio");
        }
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("nome não pode ser vazio");
        }
        codigoFipe = codigoFipe.trim();
        nome = nome.trim();
    }

    public static Marca of(String codigoFipe, String nome) {
        return new Marca(codigoFipe, nome);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return other instanceof Marca marca && codigoFipe.equals(marca.codigoFipe);
    }

    @Override
    public int hashCode() {
        return codigoFipe.hashCode();
    }
}
