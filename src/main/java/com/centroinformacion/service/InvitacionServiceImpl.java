package com.centroinformacion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centroinformacion.entity.Invitacion;
import com.centroinformacion.repository.InvitacionRepository;

@Service
public class InvitacionServiceImpl implements InvitacionService{
	
	@Autowired
	private InvitacionRepository repository;
	
	
	@Override
	public Invitacion guardaInvitacion(Invitacion obj) {
		return repository.save(obj);
	}
	
}
