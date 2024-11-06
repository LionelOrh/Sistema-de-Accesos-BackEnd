package com.centroinformacion.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // reconoce como entidad de JPA
@Data // getters y setters
@NoArgsConstructor // agregar constructor sin paràmetros
@AllArgsConstructor
@Table(name = "tipoacceso")
public class TipoAcceso {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idTipoAcceso;
	private String descripcion;
}
