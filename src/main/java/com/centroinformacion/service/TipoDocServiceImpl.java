package com.centroinformacion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centroinformacion.entity.TipoDocumento;
import com.centroinformacion.repository.TipoDocRepository;

@Service
public class TipoDocServiceImpl implements TipoDocService {
	
	@Autowired
    private TipoDocRepository tipoDocumentoRepository; 

	@Override
	public List<TipoDocumento> listaTodos() {
		return tipoDocumentoRepository.findAll(); 
	}
}
