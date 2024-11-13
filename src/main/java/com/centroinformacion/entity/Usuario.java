package com.centroinformacion.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Data 
@NoArgsConstructor 
@AllArgsConstructor
@Table(name = "usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idUsuario;
	private String nombres;
	private String apellidos;

	private String login;
	private String password;
	private String correo;
	private String celular;
	private String foto;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idTipoDoc")
	private TipoDocumento tipodocumento;
	
	private String numDoc;
	
	public String getNombreCompleto() {
		if (nombres != null && apellidos != null) {
			return nombres.concat(" ").concat(apellidos);	
		}else {
			return ""; 
		}
	}
	private int estado;
	
	// Constructor adicional para inicializar solo con el ID
		public Usuario(Integer idUsuario) {
		    this.idUsuario = idUsuario;
		}
}
