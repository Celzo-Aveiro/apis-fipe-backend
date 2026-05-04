package com.celzo_aveiro.fipe_api.domain.model;

/**
 * Veículo (modelo) do catálogo FIPE pertencente a uma {@link Marca}.
 *
 * <p>Identidade externa pelo par {@code (marca.codigoFipe, codigoFipe)}.
 * Comportamento e invariantes serão implementados no próximo passo (TDD).</p>
 */
public record Veiculo(
        String codigoFipe,
        Marca marca,
        String modelo,
        String observacoes
) {

    public Veiculo atualizar(String novoModelo, String novasObservacoes) {
        throw new UnsupportedOperationException("ainda não implementado");
    }
}
