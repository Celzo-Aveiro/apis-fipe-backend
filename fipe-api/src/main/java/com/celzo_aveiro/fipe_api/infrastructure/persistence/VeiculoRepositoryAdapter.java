package com.celzo_aveiro.fipe_api.infrastructure.persistence;

import com.celzo_aveiro.fipe_api.application.dto.VeiculoView;
import com.celzo_aveiro.fipe_api.application.port.out.VeiculoRepository;
import com.celzo_aveiro.fipe_api.domain.exception.VeiculoNaoEncontradoException;
import com.celzo_aveiro.fipe_api.domain.model.Marca;
import com.celzo_aveiro.fipe_api.domain.model.Veiculo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
class VeiculoRepositoryAdapter implements VeiculoRepository {

    private final JpaVeiculoRepository jpaVeiculoRepository;

    VeiculoRepositoryAdapter(JpaVeiculoRepository jpaVeiculoRepository) {
        this.jpaVeiculoRepository = Objects.requireNonNull(jpaVeiculoRepository);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VeiculoView> listarPorMarca(String codigoFipeMarca) {
        return jpaVeiculoRepository
                .findByMarcaCodigoFipeOrderByCodigoFipe(codigoFipeMarca)
                .stream()
                .map(e -> new VeiculoView(e.getId(), e.getCodigoFipe(), e.getModelo(), e.getObservacoes()))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Veiculo> buscarPorId(Long id) {
        return jpaVeiculoRepository.findById(id).map(this::toDomain);
    }

    @Override
    @Transactional
    public Veiculo atualizar(Long id, Veiculo veiculo) {
        var entity = jpaVeiculoRepository.findById(id)
                .orElseThrow(() -> new VeiculoNaoEncontradoException(id));
        entity.setModelo(veiculo.modelo());
        entity.setObservacoes(veiculo.observacoes());
        return toDomain(jpaVeiculoRepository.save(entity));
    }

    private Veiculo toDomain(VeiculoJpaEntity entity) {
        var marca = Marca.of(entity.getMarca().getCodigoFipe(), entity.getMarca().getNome());
        return new Veiculo(entity.getCodigoFipe(), marca, entity.getModelo(), entity.getObservacoes());
    }
}
