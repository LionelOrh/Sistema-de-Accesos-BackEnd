package com.centroinformacion.dto;
import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistroAccesoRepresentanteDTO {

    private String nombresRepresentante;
    private String apellidosRepresentante;
    private String cargoRepresentante;
    private String numDocRepresentante;
    private String tipoAcceso; // Ingreso o Salida
    private LocalDate fechaAcceso;
    private LocalTime horaAcceso;
    private String razonSocialProveedor;
}