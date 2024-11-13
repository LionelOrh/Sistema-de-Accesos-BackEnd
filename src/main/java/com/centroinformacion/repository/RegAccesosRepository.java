package com.centroinformacion.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.centroinformacion.entity.RegistroAcceso;

public interface RegAccesosRepository extends JpaRepository<RegistroAcceso, Integer>{
	List<RegistroAcceso> findByUsuario_Login(String login);

    List<RegistroAcceso> findByFechaAcceso(LocalDate fechaAcceso);

    @Query("select a from RegistroAcceso a where "
            + " a.usuario.login like ?1 and "
            + " a.fechaAcceso >= ?2 and "
            + " a.fechaAcceso <= ?3 and "
            + " a.usuario.numDoc like ?4")
    List<RegistroAcceso> listaConsultaCompleja(
            String login,
            LocalDate fechaAccesoDesde,
            LocalDate fechaAccesoHasta,
            String numDoc);
    
  //PARA TABLA REPRESENTANTE
    @Query("SELECT a FROM RegistroAcceso a WHERE a.representante.numDoc LIKE ?1 AND a.fechaAcceso >= ?2 AND a.fechaAcceso <= ?3")
    List<RegistroAcceso> listaConsultaCompleta(String numDoc, LocalDate fechaInicio, LocalDate fechaFin);

}
