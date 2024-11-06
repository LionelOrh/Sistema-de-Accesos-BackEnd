package com.centroinformacion.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.centroinformacion.entity.RegistroAcceso;
import com.centroinformacion.service.RegAccesosService;
import com.centroinformacion.util.AppSettings;

@RestController
@RequestMapping("/url/verConsultaReporte")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class RegAccesosController {
	
	@Autowired RegAccesosService regAccesosService;
	
	@GetMapping("/consultaReporteAccesos")
	@ResponseBody
	public ResponseEntity<?> consultaReporteAccesos(
	    @RequestParam(name = "login", required = false, defaultValue = "") String login,
	    @RequestParam(name = "fechaAccesoDesde", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaAccesoDesde,
	    @RequestParam(name = "fechaAccesoHasta", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaAccesoHasta,
	    @RequestParam(name = "idTipoAcceso", required = false, defaultValue = "-1") int idTipoAcceso
	) {
	    // Agrega logs para verificar los valores
	    System.out.println("Login: " + login);
	    System.out.println("Fecha Desde: " + fechaAccesoDesde);
	    System.out.println("Fecha Hasta: " + fechaAccesoHasta);
	    System.out.println("Tipo de Acceso: " + idTipoAcceso);

	    // Verifica si el valor de login es correcto y ajusta la búsqueda
	    if (login.trim().isEmpty()) {
	        login = "%"; // Si no se pasa login, busca todos los registros
	    } else {
	        login = "%" + login + "%";
	    }

	    List<RegistroAcceso> lstSalida = regAccesosService.listaConsultaCompleja(
	        login,
	        fechaAccesoDesde,
	        fechaAccesoHasta,
	        idTipoAcceso
	    );

	    return ResponseEntity.ok(lstSalida);
	}


}
