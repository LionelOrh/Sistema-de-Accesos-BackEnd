package com.centroinformacion.service;

import com.centroinformacion.dto.InvitadoDTO;
import com.centroinformacion.entity.Usuario;

public interface MotivoVisitaService {
	InvitadoDTO  buscarUsuario(String numDoc); // Buscar usuario por numDoc
	 void registrarUsuarioYMotivo(Usuario usuario, String motivo); // Registrar usuario y motivo
}
