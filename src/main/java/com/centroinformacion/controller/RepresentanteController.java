package com.centroinformacion.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.centroinformacion.entity.Representante;
import com.centroinformacion.service.RepresentanteService;
import com.centroinformacion.util.AppSettings;

@RestController
@RequestMapping("/url/representante")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class RepresentanteController {
	
	@Autowired
	private RepresentanteService representanteService;
	
	@PostMapping("/registrar")
	public ResponseEntity<?> registrarRepresentante(@RequestBody Representante representante) {
	    representante.setEstado(0); // Asignar estado por defecto
	    representanteService.registroRepresentante(representante);
	    return ResponseEntity.ok(Map.of("success", true, "message", "Representante registrado exitosamente"));
	}

}
