package com.centroinformacion.repository;

import com.centroinformacion.entity.UsuarioHasRol;
import com.centroinformacion.entity.UsuarioHasRolPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioHasRolRepository extends JpaRepository<UsuarioHasRol, UsuarioHasRolPK> {
}