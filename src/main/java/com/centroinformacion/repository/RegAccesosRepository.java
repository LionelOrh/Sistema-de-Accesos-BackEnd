package com.centroinformacion.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.centroinformacion.entity.RegistroAcceso;
import com.centroinformacion.entity.Representante;

public interface RegAccesosRepository extends JpaRepository<RegistroAcceso, Integer>{
	List<RegistroAcceso> findByUsuario_Login(String login);

    List<RegistroAcceso> findByFechaAcceso(LocalDate fechaAcceso);

    @Query("select a from RegistroAcceso a where "
            + " a.usuario.login like ?1 and "
            + " a.fechaAcceso >= ?2 and "
            + " a.fechaAcceso <= ?3 and "
            + " (?4 = -1 OR a.usuario.estado = ?4) and"
            + " a.usuario.numDoc like ?5")
    List<RegistroAcceso> listaConsultaCompleja(
            String login,
            LocalDate fechaAccesoDesde,
            LocalDate fechaAccesoHasta,
            int estado,
            String numDoc);
    
  //PARA TABLA REPRESENTANTE
    @Query("select a from Representante a where "
            + " a.numDoc like ?1 ")
    List<Representante> listaConsultaCompleta(
            String numDoc);
}
