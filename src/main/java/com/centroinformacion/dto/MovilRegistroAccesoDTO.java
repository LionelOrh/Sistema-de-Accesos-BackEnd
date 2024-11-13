package com.centroinformacion.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovilRegistroAccesoDTO {
    private LocalDate fechaAcceso;
    private LocalTime horaAcceso;
    private String tipoAcceso;

    public MovilRegistroAccesoDTO(LocalDate fechaAcceso, LocalTime horaAcceso, String tipoAcceso) {
        this.fechaAcceso = fechaAcceso;
        this.horaAcceso = horaAcceso;
        this.tipoAcceso = tipoAcceso;
    }
}
