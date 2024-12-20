package com.centroinformacion.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.centroinformacion.dto.InvitadoDTO;
import com.centroinformacion.dto.RegistroInvitadoRequestDTO;
import com.centroinformacion.service.MotivoVisitaService;
import com.centroinformacion.util.AppSettings;

@RestController
@RequestMapping("/url/motivo-invitado")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class MotivoVisitaController {
	@Autowired
	private MotivoVisitaService motivoVisitaService;

	// Endpoint para buscar si el usuario existe
	@GetMapping("/buscar")
	public ResponseEntity<?> buscarUsuario(@RequestParam String numDoc) {
	    InvitadoDTO invitadoDTO = motivoVisitaService.buscarUsuario(numDoc);
	    if (invitadoDTO != null) {
	        return ResponseEntity.ok(invitadoDTO); // Retorna el DTO limpio
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El invitado no está registrado");
	    }
	}


	// Endpoint para registrar un nuevo usuario y motivo
	@PostMapping("/registrar")
	public ResponseEntity<?> registrar(@RequestBody RegistroInvitadoRequestDTO request) {
	    try {
	        motivoVisitaService.registrarUsuarioYMotivo(request.getUsuario(), request.getMotivo());
	        return ResponseEntity.ok(Map.of("message", "Registro exitoso", "status", "success"));
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(Map.of("message", "Ocurrió un error", "status", "error", "details", e.getMessage()));
	    }
	}
}
