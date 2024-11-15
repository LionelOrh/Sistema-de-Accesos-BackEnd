package com.centroinformacion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.centroinformacion.entity.Proveedor;
import com.centroinformacion.service.ProveedorService;
import com.centroinformacion.util.AppSettings;

@RestController
@RequestMapping("/url/proveedor")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class ProveedorController {

	@Autowired
    private ProveedorService proveedorService;

    // Consultar proveedores con filtro opcional
    @GetMapping("/consultar")
    public ResponseEntity<List<Proveedor>> consultarProveedores(
            @RequestParam(value = "filtro", required = false) String filtro) {
        List<Proveedor> proveedores = proveedorService.consultarProveedores(filtro);
        return ResponseEntity.ok(proveedores);
    }
    
    @PostMapping("/registrar")
    public ResponseEntity<Proveedor> registrarProveedor(@RequestBody Proveedor proveedor) {
        Proveedor nuevoProveedor = proveedorService.registrarProveedor(proveedor);
        return ResponseEntity.ok(nuevoProveedor); // Devuelve el objeto registrado
    }


}