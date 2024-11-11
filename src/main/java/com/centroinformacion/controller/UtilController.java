package com.centroinformacion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.centroinformacion.entity.Rol;
import com.centroinformacion.entity.TipoDocumento;
import com.centroinformacion.service.RolService;
import com.centroinformacion.service.TipoDocService;
import com.centroinformacion.util.AppSettings;

@RestController
@RequestMapping("/url/util")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class UtilController {
	@Autowired
	private TipoDocService tipoDocService;
	
	@Autowired
	private RolService rolService;
	
	
	@GetMapping("/listaTipoDoc")
	@ResponseBody
	public List<TipoDocumento> listaTipoDoc() {
		return tipoDocService.listaTodos();
	}
	
	@GetMapping("/listaRol")
	@ResponseBody
	public List<Rol> listaRol(){
		return rolService.listaRol();
	}
}
