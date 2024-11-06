package com.centroinformacion.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VisitanteRequest {
	
	private Usuario usuario; 
    private String motivoVisita;
	
}
