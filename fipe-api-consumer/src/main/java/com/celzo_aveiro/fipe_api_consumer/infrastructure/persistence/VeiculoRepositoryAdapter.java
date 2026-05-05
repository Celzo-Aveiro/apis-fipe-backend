package com.celzo_aveiro.fipe_api_consumer.infrastructure.persistence;

import com.celzo_aveiro.fipe_api_consumer.application.port.out.VeiculoRepository;
import com.celzo_aveiro.fipe_api_consumer.domain.model.Veiculo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
class VeiculoRepositoryAdapter implements VeiculoRepository {

    private final JpaMarcaRepository jpaMarcaRepository;
    private final JpaVeiculoRepository jpaVeiculoRepository;

    VeiculoRepositoryAdapter(
            JpaMarcaRepository jpaMarcaRepository,
            JpaVeiculoRepository jpaVeiculoRepository
    ) {
        this.jpaMarcaRepository = Objects.requireNonNull(jpaMarcaRepository);
        this.jpaVeiculoRepository = Objects.requireNonNull(jpaVeiculoRepository);
    }

    @Override
    public void salvarTodos(List<Veiculo> veiculos) {
        if (veiculos.isEmpty()) {
            return;
        }

        Map<String, MarcaJpaEntity> marcasPorCodigo = veiculos.stream()
                .map(v -> v.marca().codigoFipe())
                .distinct()
                .collect(Collectors.toMap(
                        Function.identity(),
                        codigo -> jpaMarcaRepository
                                .findByCodigoFipe(codigo)
                                .orElseThrow(() -> new IllegalStateException(
                                        "marca com codigoFipe '" + codigo + "' nao foi persistida antes dos veiculos"
                                ))
                ));

        var entidades = veiculos.stream()
                .map(v -> mapearOuAtualizar(v, marcasPorCodigo.get(v.marca().codigoFipe())))
                .toList();

        jpaVeiculoRepository.saveAll(entidades);
    }

    private VeiculoJpaEntity mapearOuAtualizar(Veiculo veiculo, MarcaJpaEntity marca) {
        var entity = jpaVeiculoRepository
                .findByMarcaIdAndCodigoFipe(marca.getId(), veiculo.codigoFipe())
                .orElseGet(VeiculoJpaEntity::new);

        entity.setMarca(marca);
        entity.setCodigoFipe(veiculo.codigoFipe());
        entity.setModelo(veiculo.modelo());
        return entity;
    }
}
