package com.centroinformacion.controller;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.centroinformacion.entity.Invitacion;
import com.centroinformacion.entity.TipoDocumento;
import com.centroinformacion.entity.Usuario;
import com.centroinformacion.entity.UsuarioHasRol;
import com.centroinformacion.entity.UsuarioHasRolPK;
import com.centroinformacion.service.InvitacionService;
import com.centroinformacion.service.UsuarioHasRolService;
import com.centroinformacion.service.UsuarioService;
import com.centroinformacion.util.AppSettings;

@RestController
@RequestMapping("/url/invitacion")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class InvitacionController {

    @Autowired
    private InvitacionService invitacionService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioHasRolService usuarioHasRolService;

    @PostMapping("/registraUsuarioInvitado")
    @ResponseBody
    public ResponseEntity<Map<String, String>> registrarInvitado(@RequestBody Map<String, Object> payload) {
        Map<String, String> salida = new HashMap<>();

        try {
            // Extraer y validar el tipo de documento
            Map<String, Object> tipoDocMap = (Map<String, Object>) payload.get("tipodocumento");
            TipoDocumento tipoDoc = new TipoDocumento();
            if (tipoDocMap != null && tipoDocMap.get("idTipoDoc") != null) {
                Integer idTipoDoc = null;
                if (tipoDocMap.get("idTipoDoc") instanceof Integer) {
                    idTipoDoc = (Integer) tipoDocMap.get("idTipoDoc");
                } else if (tipoDocMap.get("idTipoDoc") instanceof String) {
                    idTipoDoc = Integer.parseInt((String) tipoDocMap.get("idTipoDoc"));
                }
                tipoDoc.setIdTipoDoc(idTipoDoc);
            }

            // Crear el usuario invitado
            Usuario usuarioInvitado = new Usuario();
            usuarioInvitado.setNombres((String) payload.get("nombres"));
            usuarioInvitado.setApellidos((String) payload.get("apellidos"));
            usuarioInvitado.setCelular((String) payload.get("celular"));
            usuarioInvitado.setCorreo((String) payload.get("correo"));
            usuarioInvitado.setNumDoc((String) payload.get("numDoc"));
            usuarioInvitado.setLogin("No aplica");
            usuarioInvitado.setPassword("No aplica");
            usuarioInvitado.setFoto("No aplica");
            usuarioInvitado.setTipodocumento(tipoDoc);
            usuarioInvitado.setEstado(0);

            // Guardar el usuario invitado
            Usuario nuevoUsuario = usuarioService.guardarUsuarioInvitado(usuarioInvitado);

            // Asignar rol de invitado
            UsuarioHasRolPK usuarioHasRolPk = new UsuarioHasRolPK();
            usuarioHasRolPk.setIdUsuario(nuevoUsuario.getIdUsuario());
            usuarioHasRolPk.setIdRol(7);
            UsuarioHasRol usuarioHasRol = new UsuarioHasRol();
            usuarioHasRol.setUsuarioHasRolPk(usuarioHasRolPk);
            usuarioHasRolService.guardarUsuarioHasRolInv(usuarioHasRol);

            // Crear la invitación
            Invitacion objInvitacion = new Invitacion();
            objInvitacion.setMotivo((String) payload.get("motivo"));
            objInvitacion.setFechaInvitacion(new Date());
            objInvitacion.setUsuarioInvitado(nuevoUsuario);

            // Obtener y asignar el usuario que registra la visita
            Object idUsuarioRegVisitaObj = payload.get("idUsuarioRegVisita");
            if (idUsuarioRegVisitaObj != null) {
                int idUsuarioRegVisita;
                if (idUsuarioRegVisitaObj instanceof Integer) {
                    idUsuarioRegVisita = (Integer) idUsuarioRegVisitaObj;
                } else if (idUsuarioRegVisitaObj instanceof String) {
                    idUsuarioRegVisita = Integer.parseInt((String) idUsuarioRegVisitaObj);
                } else if (idUsuarioRegVisitaObj instanceof Double) {
                    idUsuarioRegVisita = ((Double) idUsuarioRegVisitaObj).intValue();
                } else {
                    throw new IllegalArgumentException("Tipo de ID de usuario no válido");
                }
                
                Usuario usuarioRegVisita = usuarioService.buscaPorId(idUsuarioRegVisita);
                if (usuarioRegVisita != null) {
                    objInvitacion.setUsuarioRegVisita(usuarioRegVisita);
                }
            }

            // Guardar la invitación
            Invitacion invitacionGuardada = invitacionService.guardaInvitacion(objInvitacion);

            if (invitacionGuardada == null) {
                salida.put("mensaje", "Error en el registro");
            } else {
                salida.put("mensaje", "Se registró la invitación con el ID ==> " + invitacionGuardada.getIdInvitacion());
                salida.put("mensajeUsuario", "Se registró al usuario con el ID ==> " + nuevoUsuario.getIdUsuario());
            }

        } catch (Exception e) {
            e.printStackTrace();
            salida.put("mensaje", AppSettings.MENSAJE_REG_ERROR);
        }

        return ResponseEntity.ok(salida);
    }
    
    
    @GetMapping("/validar-numDoc")
    public ResponseEntity<?> validarNumDoc(@RequestParam String numDoc) {
        boolean existe = invitacionService.validarNumDoc(numDoc);
        return ResponseEntity.ok(Collections.singletonMap("existe", existe));
    }

}
