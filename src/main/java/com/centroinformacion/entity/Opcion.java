package com.centroinformacion.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Data 
@NoArgsConstructor 
@AllArgsConstructor
@Table(name = "opcion")
public class Opcion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idOpcion;
	private String nombre;
	private String ruta;
	private int estado;
	private int tipo;

}
