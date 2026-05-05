package com.celzo_aveiro.fipe_api.infrastructure.persistence;

import com.celzo_aveiro.fipe_api.application.port.out.MarcaConsultaRepository;
import com.celzo_aveiro.fipe_api.domain.model.Marca;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Component
class MarcaConsultaRepositoryAdapter implements MarcaConsultaRepository {

    private final JpaMarcaRepository jpaMarcaRepository;

    MarcaConsultaRepositoryAdapter(JpaMarcaRepository jpaMarcaRepository) {
        this.jpaMarcaRepository = Objects.requireNonNull(jpaMarcaRepository);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Marca> listarTodas() {
        return jpaMarcaRepository.findAll().stream()
                .map(e -> Marca.of(e.getCodigoFipe(), e.getNome()))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorCodigoFipe(String codigoFipe) {
        return jpaMarcaRepository.existsByCodigoFipe(codigoFipe);
    }
}
