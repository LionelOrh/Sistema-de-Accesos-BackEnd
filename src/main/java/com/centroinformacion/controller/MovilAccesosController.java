package com.centroinformacion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.centroinformacion.entity.RegistroAcceso;
import com.centroinformacion.service.RegAccesosService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/url/mobile/acceso")
public class MovilAccesosController {

    @Autowired
    private RegAccesosService registroAccesoService;

    @GetMapping("/filtrarAccesos")
    public ResponseEntity<?> filtrarAccesos(@RequestParam Integer idUsuario, 
                                             @RequestParam(required = false) String fecha) {
        // Validación de parámetros
        if (idUsuario == null || idUsuario <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("El parámetro 'idUsuario' es obligatorio y debe ser un valor válido.");
        }

        Optional<LocalDate> fechaAcceso = Optional.ofNullable(fecha).map(LocalDate::parse);

        // Llamada al servicio para obtener los accesos filtrados
        List<RegistroAcceso> accesos = registroAccesoService.obtenerAccesosFiltrados(idUsuario, fechaAcceso);

        if (accesos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("No se encontraron registros de acceso para los parámetros proporcionados.");
        }

        return ResponseEntity.ok(accesos);
    }
}
