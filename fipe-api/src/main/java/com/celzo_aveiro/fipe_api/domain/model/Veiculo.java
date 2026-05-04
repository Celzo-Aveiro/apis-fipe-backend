package com.celzo_aveiro.fipe_api.domain.model;

/**
 * Veículo (modelo) do catálogo FIPE pertencente a uma {@link Marca}.
 *
 * <p>Identidade externa pelo par {@code (marca.codigoFipe, codigoFipe)} — a FIPE reusa
 * códigos de modelo entre marcas. Imutável; modelo e código são obrigatórios,
 * observações são livres e podem vir em branco/nulo. Espaços nas bordas são
 * normalizados.</p>
 */
public record Veiculo(
        String codigoFipe,
        Marca marca,
        String modelo,
        String observacoes
) {

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
        observacoes = (observacoes == null || observacoes.isBlank()) ? null : observacoes.trim();
    }

    /**
     * Retorna uma nova instância com modelo e observações atualizados,
     * preservando código FIPE e marca. Cobre o caso de uso 1.8 do teste.
     */
    public Veiculo atualizar(String novoModelo, String novasObservacoes) {
        return new Veiculo(codigoFipe, marca, novoModelo, novasObservacoes);
    }
}
