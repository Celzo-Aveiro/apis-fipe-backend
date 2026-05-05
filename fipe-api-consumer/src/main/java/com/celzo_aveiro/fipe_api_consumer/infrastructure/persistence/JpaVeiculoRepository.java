package com.celzo_aveiro.fipe_api_consumer.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface JpaVeiculoRepository extends JpaRepository<VeiculoJpaEntity, Long> {

    Optional<VeiculoJpaEntity> findByMarcaIdAndCodigoFipe(Long marcaId, String codigoFipe);
}
