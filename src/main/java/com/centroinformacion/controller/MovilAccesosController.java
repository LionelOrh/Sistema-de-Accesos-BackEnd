package com.centroinformacion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.centroinformacion.dto.MovilRegistroAccesoDTO;
import com.centroinformacion.service.RegAccesosService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/acceso")
public class MovilAccesosController {

    @Autowired
    private RegAccesosService registroAccesoService;

    @GetMapping("/filtrarAccesos")
    public List<MovilRegistroAccesoDTO> buscarAccesos(
            @RequestParam Integer idUsuario, 
            @RequestParam(required = false) String fecha) {

        if (fecha != null && !fecha.isEmpty()) {
            // Convert the date string to LocalDate
            LocalDate fechaAcceso = LocalDate.parse(fecha);
            // Search by idUsuario and fecha
            return registroAccesoService.buscarPorUsuarioYFecha(idUsuario, fechaAcceso);
        } else {
            // If no date is provided, search by idUsuario only
            return registroAccesoService.buscarPorUsuario(idUsuario);
        }
    }
}
