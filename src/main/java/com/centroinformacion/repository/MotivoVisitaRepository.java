package com.centroinformacion.repository;

import com.centroinformacion.entity.MotivoVisita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotivoVisitaRepository extends JpaRepository<MotivoVisita, Long> {
}
