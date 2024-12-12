package com.centroinformacion.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.centroinformacion.dto.RegistroAccesoIEDTO;
import com.centroinformacion.dto.RegistroAccesoRepresentanteDTO;
import com.centroinformacion.entity.RegistroAcceso;

public interface RegAccesosRepository extends JpaRepository<RegistroAcceso, Integer>{
	List<RegistroAcceso> findByUsuario_Login(String login);

    List<RegistroAcceso> findByFechaAcceso(LocalDate fechaAcceso);

    @Query("SELECT new com.centroinformacion.dto.RegistroAccesoIEDTO("
            + "a.usuario.login, "
            + "a.usuario.nombres, "
            + "a.usuario.apellidos, "
            + "a.usuario.numDoc, "
            + "a.fechaAcceso, "
            + "a.horaAcceso, "
            + "a.tipoAcceso"
            + ") FROM RegistroAcceso a WHERE "
            + " (a.usuario.login like ?1 or a.usuario.numDoc like ?1) AND "
            + " a.fechaAcceso >= ?2 AND "
            + " a.fechaAcceso <= ?3")
    List<RegistroAccesoIEDTO> listaConsultaCompleja(
    		  String loginOrNumDoc, 
              LocalDate fechaAccesoDesde, 
              LocalDate fechaAccesoHasta);


    
    @Query("SELECT new com.centroinformacion.dto.RegistroAccesoRepresentanteDTO("
            + "r.representante.nombres,"
            + "r.representante.apellidos,"
            + "r.representante.cargo,"
            + "r.representante.numDoc,"
            + "r.tipoAcceso,"
            + "r.fechaAcceso,"
            + "r.horaAcceso,"
            + "r.representante.proveedor.razonSocial"
            + ") FROM RegistroAcceso r WHERE r.representante.numDoc LIKE :numDoc "
                + "AND r.fechaAcceso BETWEEN :fechaInicio AND :fechaFin")
    List<RegistroAccesoRepresentanteDTO> listaConsultaCompleta(String numDoc, 
                                                          LocalDate fechaInicio, 
                                                          LocalDate fechaFin);

    //PARA ACCESOS APP MOVIL
    List<RegistroAcceso> findByUsuario_IdUsuarioAndFechaAcceso(Integer idUsuario, LocalDate fechaAcceso);
    
    List<RegistroAcceso> findByUsuario_IdUsuario(Integer idUsuario);
}
