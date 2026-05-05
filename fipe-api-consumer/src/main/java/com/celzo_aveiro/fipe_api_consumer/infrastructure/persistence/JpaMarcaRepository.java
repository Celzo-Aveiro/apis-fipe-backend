package com.celzo_aveiro.fipe_api_consumer.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface JpaMarcaRepository extends JpaRepository<MarcaJpaEntity, Long> {

    Optional<MarcaJpaEntity> findByCodigoFipe(String codigoFipe);
}
