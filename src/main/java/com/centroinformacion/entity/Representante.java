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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Data 
@NoArgsConstructor 
@AllArgsConstructor
@Table(name = "representante")
@Getter
@Setter
public class Representante {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idRepresentante;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idProveedor")
	private Proveedor proveedor;
	
	private String nombres;
	private String apellidos;
	private String cargo;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idTipoDoc")
	private TipoDocumento tipoDocumento;
	
	private int estado;
	
	private String numDoc;
	
	public Representante(Integer idRepresentante) {
	    this.idRepresentante = idRepresentante;
	}

}
