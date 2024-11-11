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
import com.centroinformacion.entity.Representante;
import com.centroinformacion.entity.TipoDocumento;
import com.centroinformacion.service.ProveedorService;
import com.centroinformacion.service.RepresentanteService;
import com.centroinformacion.service.TipoDocService;
import com.centroinformacion.util.AppSettings;

@RestController
@RequestMapping("/url/verAccesoProveedor")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private RepresentanteService representanteService;
    @Autowired
    private TipoDocService tipoDocService;


    @PostMapping("/registraProveedor")
    @ResponseBody
    public ResponseEntity<Map<String, String>> registroProveedorRepresentante(@RequestBody Map<String, Object> payload) {
        Map<String, String> salida = new HashMap<>();

        try {
            
            // Crear y registrar el proveedor
            Proveedor proveedor = new Proveedor();
            proveedor.setRazonSocial((String) payload.get("razonSocial"));
            proveedor.setRuc((String) payload.get("ruc"));
            proveedor.setDescripcion((String) payload.get("descripcion"));

            Proveedor proveedorGuardado = proveedorService.registroProveedor(proveedor);

            if (proveedorGuardado == null) {
                salida.put("mensaje", "Error al registrar el proveedor");
            } else {
                // Crear y registrar el representante
                Representante representante = new Representante();
                representante.setNombres((String) payload.get("nombres"));
                representante.setApellidos((String) payload.get("apellidos"));
                representante.setCargo((String) payload.get("cargo"));
                representante.setNumDoc((String) payload.get("numDoc"));
                
                // Asignar el proveedor guardado al representante
                representante.setProveedor(proveedorGuardado);

                // Obtener el tipo de documento y asignarlo al representante
                Integer tipoDocId = null;
                try {
                    tipoDocId = Integer.parseInt((String) payload.get("tipoDocumento"));  // Convertir el tipoDocumento a Integer
                } catch (NumberFormatException e) {
                    salida.put("mensaje", "Tipo de documento inválido.");
                    return ResponseEntity.badRequest().body(salida);
                }

                if (tipoDocId != null) {
                    TipoDocumento tipoDoc = tipoDocService.obtenerTipoDocumentoPorId(tipoDocId);
                    if (tipoDoc == null) {
                        salida.put("mensaje", "Tipo de documento no encontrado");
                        return ResponseEntity.badRequest().body(salida);
                    }
                    representante.setTipoDocumento(tipoDoc);  // Asignamos el tipo de documento al representante
                }

                Representante representanteGuardado = representanteService.registroRepresentante(representante);

                if (representanteGuardado == null) {
                    salida.put("mensaje", "Proveedor registrado, pero error al registrar el representante");
                } else {
                    salida.put("mensaje", "Proveedor y representante registrados con éxito");
                    salida.put("idProveedor", String.valueOf(proveedorGuardado.getIdProveedor()));
                    salida.put("idRepresentante", String.valueOf(representanteGuardado.getIdRepresentante()));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            salida.put("mensaje", AppSettings.MENSAJE_REG_ERROR);
        }

        return ResponseEntity.ok(salida);
    }

}