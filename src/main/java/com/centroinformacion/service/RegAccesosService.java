package com.centroinformacion.service;

import java.time.LocalDate;
import java.util.List;

import com.centroinformacion.entity.RegistroAcceso;

public interface RegAccesosService {
	 List<RegistroAcceso> listaPorLogin(String login);
	 List<RegistroAcceso> listaPorFechaAcceso(LocalDate fechaAcceso);
	 List<RegistroAcceso> listaConsultaCompleja(String login, LocalDate fechaAccesoDesde, LocalDate fechaAccesoHasta, int idTipoAcceso,String numDoc);   
	 List<RegistroAcceso> listaConsultaCompleta(String numDoc); 
}   
