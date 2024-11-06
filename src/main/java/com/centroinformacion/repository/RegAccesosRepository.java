package com.centroinformacion.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.centroinformacion.entity.RegistroAcceso;
import com.centroinformacion.entity.TipoAcceso;

public interface RegAccesosRepository extends JpaRepository<RegistroAcceso, Integer>{
	List<RegistroAcceso> findByUsuario_Login(String login);

    List<RegistroAcceso> findByTipoAcceso(TipoAcceso idTipoAcceso);

    List<RegistroAcceso> findByFechaAcceso(LocalDate fechaAcceso);

    @Query("select a from RegistroAcceso a where "
            + " a.usuario.login like ?1 and "
            + " a.fechaAcceso >= ?2 and "
            + " a.fechaAcceso <= ?3 and "
            + " (?4 = -1 or a.tipoAcceso.idTipoAcceso = ?4)")
    List<RegistroAcceso> listaConsultaCompleja(
            String login,
            LocalDate fechaAccesoDesde,
            LocalDate fechaAccesoHasta,
            int idTipoAcceso);
}
