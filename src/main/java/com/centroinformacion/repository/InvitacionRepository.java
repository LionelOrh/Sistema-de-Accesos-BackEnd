package com.centroinformacion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.centroinformacion.entity.Invitacion;

public interface InvitacionRepository extends JpaRepository<Invitacion, Long>{
    Optional<Invitacion> findByUsuarioInvitado_IdUsuario(Long idUsuarioInvitado);

}
