package com.centroinformacion.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonFormat;
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

@Entity // reconoce como entidad de JPA
@Data // getters y setters
@NoArgsConstructor // agregar constructor sin paràmetros
@AllArgsConstructor
@Table(name = "registroacceso")
public class RegistroAcceso {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idRegistro;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idUsuarioRegAcceso")
	private Usuario usuarioRegAcceso;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idUsuario")
	private Usuario usuario;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idRepresentante")
	private Representante representante;

	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate fechaAcceso;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm:ss")
	private LocalTime horaAcceso;
	
	public String getReporteFecha() {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    return fechaAcceso != null ? fechaAcceso.format(formatter) : "Fecha no disponible";
	}

}