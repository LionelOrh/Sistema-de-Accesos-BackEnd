package com.centroinformacion.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistroAccesoIEDTO {
	 	private String codigoIE;
	    private String nombresIE;
	    private String apellidosIE;
	    private String numDocIE;
	    private LocalDate fechaAcceso;
	    private LocalTime horaAcceso;
	    private String tipoAcceso; // Ingreso o Salida
}
