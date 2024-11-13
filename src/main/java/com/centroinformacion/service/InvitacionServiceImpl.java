package com.centroinformacion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centroinformacion.entity.Invitacion;
import com.centroinformacion.repository.InvitacionRepository;
import com.centroinformacion.repository.RepresentanteRepository;
import com.centroinformacion.repository.UsuarioRepository;

@Service
public class InvitacionServiceImpl implements InvitacionService{
	
	@Autowired
	private InvitacionRepository repository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private RepresentanteRepository representanteRepository;
	
	@Override
	public Invitacion guardaInvitacion(Invitacion obj) {
		return repository.save(obj);
	}
	
	@Override
	public boolean validarNumDoc(String numDoc) {
	    // Verifica si el numDoc existe en Usuario o Representante
	    boolean existeEnUsuario = usuarioRepository.existsByNumDoc(numDoc);
	    boolean existeEnRepresentante = representanteRepository.existsByNumDoc(numDoc);
	    return existeEnUsuario || existeEnRepresentante;
	}
	
}
