package com.centroinformacion.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centroinformacion.repository.RepresentanteRepository;
import com.centroinformacion.repository.UsuarioRepository;

@Service
public class UtilServiceImpl implements UtilService{
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private RepresentanteRepository representanteRepository;
	
	@Override
	public boolean validarNumDoc(String numDoc) {
	    // Verifica si el numDoc existe en Usuario o Representante
	    boolean existeEnUsuario = usuarioRepository.existsByNumDoc(numDoc);
	    boolean existeEnRepresentante = representanteRepository.existsByNumDoc(numDoc);
	    return existeEnUsuario || existeEnRepresentante;
	}
}
