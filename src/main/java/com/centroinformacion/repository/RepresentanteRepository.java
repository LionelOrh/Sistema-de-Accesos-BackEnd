package com.centroinformacion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.centroinformacion.entity.Representante;

public interface RepresentanteRepository extends  JpaRepository<Representante,Integer> {
	
	@Query("SELECT r FROM Representante r WHERE r.numDoc = :codigo")
	List<Representante> buscarPorCodigo(@Param("codigo") String codigo);

}
