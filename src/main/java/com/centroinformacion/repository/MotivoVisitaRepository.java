package com.centroinformacion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.centroinformacion.entity.MotivoVisita;

public interface MotivoVisitaRepository extends JpaRepository<MotivoVisita, Long> {

    Optional<MotivoVisita> findByUsuarioVisitante_IdUsuario(Long idUsuarioVisitante);

}
