package com.centroinformacion.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.centroinformacion.entity.Proveedor;
import com.centroinformacion.service.ProveedorService;
import com.centroinformacion.util.AppSettings;

@RestController
@RequestMapping("/url/verAccesoProveedor")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class ProveedorController {
	
	@Autowired
	private ProveedorService proveedorService;
	
	
		
		@PostMapping("/registraProveedor")
		@ResponseBody
		public  ResponseEntity<?> registroProveedor(@RequestBody Proveedor obj){
			Map<String, Object> salida = new HashMap<>();
			try {
				
				
				Proveedor objSalida = proveedorService.registroProveedor(obj);
				if(objSalida == null) {
					salida.put("mensaje",AppSettings.MENSAJE_REG_ERROR);
					
				}else {
					salida.put("mensaje",AppSettings.MENSAJE_REG_EXITOSO);
				}
			} catch (Exception e) {
				e.printStackTrace();
				salida.put("mensaje", AppSettings.MENSAJE_REG_ERROR);
			}
			return ResponseEntity.ok(salida);
		}
}
