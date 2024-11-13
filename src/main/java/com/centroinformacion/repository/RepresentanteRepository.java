package com.centroinformacion.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.centroinformacion.entity.Representante;

public interface RepresentanteRepository extends  JpaRepository<Representante,Integer> {
	
	@Query("SELECT r FROM Representante r WHERE r.numDoc = :codigo")
	List<Representante> buscarPorCodigo(@Param("codigo") String codigo);
	Optional<Representante> findById(int idRepresentante);
	
	@Modifying
	@Query("UPDATE Representante r SET r.estado = CASE WHEN r.estado = 0 THEN 1 ELSE 0 END WHERE r.idRepresentante = :idRepresentante")
	void actualizarEstadoRepresentante(@Param("idRepresentante") Integer idRepresentante);
	
	@Query("SELECT r.estado FROM Representante r WHERE r.idRepresentante = :idRepresentante")
	Integer obtenerEstadoRepresentante(@Param("idRepresentante") Integer idRepresentante);


}
