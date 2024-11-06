package com.centroinformacion.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centroinformacion.entity.RegistroAcceso;
import com.centroinformacion.entity.TipoAcceso;
import com.centroinformacion.repository.RegAccesosRepository;
import com.centroinformacion.service.RegAccesosService;

@Service
public class RegAccesosServiceImpl implements RegAccesosService{

	 @Autowired
	 private RegAccesosRepository repository;

	@Override
	public List<RegistroAcceso> listaPorLogin(String login) {
		  return repository.findByUsuario_Login(login);
	}

	@Override
	public List<RegistroAcceso> listaPorTipoAcceso(TipoAcceso tipoAcceso) {
		 return repository.findByTipoAcceso(tipoAcceso);
	}

	@Override
	public List<RegistroAcceso> listaPorFechaAcceso(LocalDate fechaAcceso) {
		  return repository.findByFechaAcceso(fechaAcceso);
	}

	@Override
	public List<RegistroAcceso> listaConsultaCompleja(String login, LocalDate fechaAccesoDesde,
			LocalDate fechaAccesoHasta, int idTipoAcceso) {
		return repository.listaConsultaCompleja(login, fechaAccesoDesde, fechaAccesoHasta, idTipoAcceso);
	}
	 
	

}
