package com.centroinformacion.controller;

import com.centroinformacion.entity.MotivoVisita;
import com.centroinformacion.entity.Rol;
import com.centroinformacion.entity.Usuario;
import com.centroinformacion.entity.UsuarioHasRol;
import com.centroinformacion.entity.UsuarioHasRolPK;
import com.centroinformacion.entity.VisitanteDTO;
import com.centroinformacion.service.MotivoVisitaService;
import com.centroinformacion.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/visitantes")
public class VisitanteController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MotivoVisitaService motivoVisitaService;

    // Endpoint para registrar un visitante
    @PostMapping("/registro")
    public ResponseEntity<String> registrarVisitante(@RequestBody VisitanteDTO visitanteDTO) {
        try {
            // Paso 1: Crear el usuario
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setNombres(visitanteDTO.getNombres());
            nuevoUsuario.setApellidos(visitanteDTO.getApellidos());
            nuevoUsuario.setCelular(visitanteDTO.getCelular());
            nuevoUsuario.setCorreo(visitanteDTO.getCorreo());
            nuevoUsuario.setNumDoc(visitanteDTO.getNumDoc());
            nuevoUsuario.setTipodocumento(visitanteDTO.getTipoDocumento());

            // Guardar el usuario
            Usuario usuarioGuardado = usuarioService.guardarUsuario(nuevoUsuario);

            // Paso 2: Asignar el rol de "Visitante" (idRol = 8)
            Rol rolVisitante = new Rol();
            rolVisitante.setIdRol(8);  // ID del rol Visitante

            // Crear la clave compuesta para UsuarioHasRol
            UsuarioHasRolPK usuarioHasRolPK = new UsuarioHasRolPK();
            usuarioHasRolPK.setIdUsuario(usuarioGuardado.getIdUsuario());
            usuarioHasRolPK.setIdRol(rolVisitante.getIdRol());

            // Crear la entidad UsuarioHasRol
            UsuarioHasRol usuarioRol = new UsuarioHasRol();
            usuarioRol.setUsuarioHasRolPk(usuarioHasRolPK);
            usuarioRol.setUsuario(usuarioGuardado);
            usuarioRol.setRol(rolVisitante);

            // Paso 3: Guardar la relación usuario-tiene-rol
            usuarioService.asignarRol(usuarioRol);

            // Paso 4: Registrar el motivo de visita
            MotivoVisita motivoVisita = new MotivoVisita();
            motivoVisita.setUsuarioVisitante(usuarioGuardado);
            motivoVisita.setMotivoVisita(visitanteDTO.getMotivoVisita());
            motivoVisita.setFechaRegistro(new Date());

            // Guardar el motivo de visita
            motivoVisitaService.registrarMotivoVisita(motivoVisita);

            return ResponseEntity.ok("Visitante registrado con éxito");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al registrar el visitante");
        }
    }
}
