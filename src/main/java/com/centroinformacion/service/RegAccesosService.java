package com.centroinformacion.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.centroinformacion.dto.PreRegistroConsultaDTO;
import com.centroinformacion.dto.RegistroRequest;
import com.centroinformacion.entity.RegistroAcceso;

public interface RegAccesosService {
	 List<RegistroAcceso> listaPorLogin(String login);
	 List<RegistroAcceso> listaPorFechaAcceso(LocalDate fechaAcceso);
	 List<RegistroAcceso> listaConsultaCompleja(String loginOrNumDoc, LocalDate fechaAccesoDesde, LocalDate fechaAccesoHasta);   
	 List<RegistroAcceso> listaConsultaCompleta(String numDoc, LocalDate fechaAccesoDesde, LocalDate fechaAccesoHasta); 
	 
	 PreRegistroConsultaDTO buscarPorCodigo(String codigo);
	 
	 void registrarAcceso(RegistroRequest request);
	 
	//PARA ACCESOS APP MOVIL
	 public List<RegistroAcceso> obtenerAccesosFiltrados(Integer idUsuario, Optional<LocalDate> fecha);
}   
