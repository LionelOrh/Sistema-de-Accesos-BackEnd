package com.centroinformacion.dto;

import com.centroinformacion.entity.Usuario;

public class InvitadoDTO {
	private int idUsuario;
	private String nombres;
    private String apellidos;
    private String celular;
    private String correo;
    private String numDoc;
    private int idTipoDoc;
    
    
 // Constructor que convierte Usuario a UsuarioDTO
    public InvitadoDTO(Usuario usuario) {
    	 this.idUsuario = usuario.getIdUsuario();
        this.nombres = usuario.getNombres();
        this.apellidos = usuario.getApellidos();
        this.celular = usuario.getCelular();
        this.correo = usuario.getCorreo();
        this.numDoc = usuario.getNumDoc();
        if (usuario.getTipodocumento() != null) {
            this.idTipoDoc = usuario.getTipodocumento().getIdTipoDoc();
        }
    }

    // Getters
    public int getIdUsuario() { return idUsuario; }
    public String getNombres() { return nombres; }
    public String getApellidos() { return apellidos; }
    public String getCelular() { return celular; }
    public String getCorreo() { return correo; }
    public String getNumDoc() { return numDoc; }
    public int getIdTipoDoc() { return idTipoDoc; }
}
