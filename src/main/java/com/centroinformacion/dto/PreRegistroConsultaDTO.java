package com.centroinformacion.dto;

public class PreRegistroConsultaDTO {
	private String codigo;          // Login o "no aplica"
    private String identificacion;  // Número de documento
    private String nombres;
    private String apellidos;
    private String estado;          // Activo o Inactivo
    private String foto;            // URL de la foto (solo para usuarios)
    private int id;                 // ID del usuario o representante
    private String tipo;            // "usuario" o "representante"

    // Getters y Setters
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getIdentificacion() { return identificacion; }
    public void setIdentificacion(String identificacion) { this.identificacion = identificacion; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}