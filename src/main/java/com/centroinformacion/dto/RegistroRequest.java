package com.centroinformacion.dto;

public class RegistroRequest {
	private Integer idUsuario; // ID del usuario, si aplica
    private Integer idRepresentante; // ID del representante, si aplica
    private Integer idUsuarioRegAcceso; // ID del usuario logueado

    // Getters y Setters
    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdRepresentante() {
        return idRepresentante;
    }

    public void setIdRepresentante(Integer idRepresentante) {
        this.idRepresentante = idRepresentante;
    }

    public Integer getIdUsuarioRegAcceso() {
        return idUsuarioRegAcceso;
    }

    public void setIdUsuarioRegAcceso(Integer idUsuarioRegAcceso) {
        this.idUsuarioRegAcceso = idUsuarioRegAcceso;
    }
}
