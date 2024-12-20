package com.centroinformacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.centroinformacion.entity.EstadoMatricula;

public interface EstadoMatriculaRepository extends JpaRepository<EstadoMatricula, Long>{
	
	 // Método para buscar por idUsuario
    EstadoMatricula findByUsuarioIdUsuario(Integer idUsuario);
}
