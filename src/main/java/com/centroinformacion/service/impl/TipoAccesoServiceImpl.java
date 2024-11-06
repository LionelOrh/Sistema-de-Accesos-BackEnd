package com.centroinformacion.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centroinformacion.entity.TipoAcceso;
import com.centroinformacion.repository.TipoAccesoRepository;
import com.centroinformacion.service.TipoAccesoService;

@Service
public class TipoAccesoServiceImpl implements TipoAccesoService{
	@Autowired
	private TipoAccesoRepository tipoAccesoRepository;
	
	@Override
	public List<TipoAcceso> listaTipoAcceso(){
		return tipoAccesoRepository.findAll();
	}
}
