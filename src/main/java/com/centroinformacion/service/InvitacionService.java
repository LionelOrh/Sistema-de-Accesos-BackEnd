package com.centroinformacion.service;

import java.util.Map;

import com.centroinformacion.entity.Invitacion;

public interface InvitacionService {
	Invitacion guardaInvitacion (Invitacion obj);
	
	Map<String, String> registrarUsuarioEInvitacion(Map<String, Object> payload);
}
