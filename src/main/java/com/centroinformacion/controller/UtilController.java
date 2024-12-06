package com.centroinformacion.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.centroinformacion.entity.Rol;
import com.centroinformacion.entity.TipoDocumento;
import com.centroinformacion.service.RolService;
import com.centroinformacion.service.TipoDocService;
import com.centroinformacion.util.AppSettings;
import com.centroinformacion.util.UtilService;

@RestController
@RequestMapping("/url/util")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class UtilController {
	@Autowired
	private TipoDocService tipoDocService;
	
	@Autowired
	private RolService rolService;
	
	@Autowired
	private UtilService utilService;
	
	
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
	
	@GetMapping("/validar-numDoc")
    public ResponseEntity<?> validarNumDoc(@RequestParam String numDoc) {
        boolean existe = utilService.validarNumDoc(numDoc);
        return ResponseEntity.ok(Collections.singletonMap("existe", existe));
    }
	
	@GetMapping("/validar-numDocUsuario")
    public ResponseEntity<?> validarNumDocUsuario(@RequestParam String numDoc) {
        boolean existe = utilService.validarNumDocUsuario(numDoc);
        return ResponseEntity.ok(Collections.singletonMap("existe", existe));
    }
	
	@GetMapping("/validar-numDocRepresentante")
    public ResponseEntity<?> validarNumDocRepresentante(@RequestParam String numDoc) {
        boolean existe = utilService.validarNumDocRepresentante(numDoc);
        return ResponseEntity.ok(Collections.singletonMap("existe", existe));
    }
	
	@GetMapping("/validar-ruc")
    public ResponseEntity<?> validarRuc(@RequestParam String ruc) {
        boolean existe = utilService.validarRuc(ruc);
        return ResponseEntity.ok(Collections.singletonMap("existe", existe));
    }
	
	@GetMapping("/validar-razonSocial")
    public ResponseEntity<?> validarRazonSocial(@RequestParam String razonSocial) {
        boolean existe = utilService.validarRazonSocial(razonSocial);
        return ResponseEntity.ok(Collections.singletonMap("existe", existe));
    }
	
	
}
