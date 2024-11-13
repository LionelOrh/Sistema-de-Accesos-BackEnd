package com.centroinformacion.service;

import java.time.LocalDate;
import java.util.List;

import com.centroinformacion.dto.PreRegistroConsultaDTO;
import com.centroinformacion.dto.RegistroRequest;
import com.centroinformacion.entity.RegistroAcceso;

public interface RegAccesosService {
	 List<RegistroAcceso> listaPorLogin(String login);
	 List<RegistroAcceso> listaPorFechaAcceso(LocalDate fechaAcceso);
	 List<RegistroAcceso> listaConsultaCompleja(String login, LocalDate fechaAccesoDesde, LocalDate fechaAccesoHasta,String numDoc);   
	 List<RegistroAcceso> listaConsultaCompleta(String numDoc); 
	 
	 PreRegistroConsultaDTO buscarPorCodigo(String codigo);
	 
	 void registrarAcceso(RegistroRequest request);
}   