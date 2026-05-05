package com.celzo_aveiro.fipe_api_consumer.infrastructure.persistence;

import com.celzo_aveiro.fipe_api_consumer.application.port.out.MarcaRepository;
import com.celzo_aveiro.fipe_api_consumer.domain.model.Marca;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
class MarcaRepositoryAdapter implements MarcaRepository {

    private final JpaMarcaRepository jpaMarcaRepository;

    MarcaRepositoryAdapter(JpaMarcaRepository jpaMarcaRepository) {
        this.jpaMarcaRepository = Objects.requireNonNull(jpaMarcaRepository);
    }

    @Override
    public Marca salvar(Marca marca) {
        var entity = jpaMarcaRepository
                .findByCodigoFipe(marca.codigoFipe())
                .orElseGet(MarcaJpaEntity::new);

        entity.setCodigoFipe(marca.codigoFipe());
        entity.setNome(marca.nome());

        var salva = jpaMarcaRepository.save(entity);
        return Marca.of(salva.getCodigoFipe(), salva.getNome());
    }
}
