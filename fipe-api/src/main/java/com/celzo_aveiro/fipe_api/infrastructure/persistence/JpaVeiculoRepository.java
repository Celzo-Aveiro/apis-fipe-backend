package com.celzo_aveiro.fipe_api.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface JpaVeiculoRepository extends JpaRepository<VeiculoJpaEntity, Long> {

    List<VeiculoJpaEntity> findByMarcaCodigoFipeOrderByCodigoFipe(String codigoFipeMarca);
}
