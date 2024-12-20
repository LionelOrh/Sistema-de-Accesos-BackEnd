package com.centroinformacion.dto;

import com.centroinformacion.entity.Usuario;

public class RegistroInvitadoRequestDTO {
	private Usuario usuario;
	private String motivo;
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
	
}
