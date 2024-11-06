package com.centroinformacion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centroinformacion.entity.UsuarioHasRol;
import com.centroinformacion.repository.UsuarioHasRolRepository;

@Service
public class UsuarioHasRolServiceImpl implements UsuarioHasRolService{
	
	@Autowired
	private UsuarioHasRolRepository repository;

	@Override
	public UsuarioHasRol guardarUsuarioHasRolInv(UsuarioHasRol obj) {
		return repository.save(obj);
	}

}
