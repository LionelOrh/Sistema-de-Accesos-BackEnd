package com.centroinformacion.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centroinformacion.dto.InvitadoDTO;
import com.centroinformacion.entity.MotivoVisita;
import com.centroinformacion.entity.Rol;
import com.centroinformacion.entity.Usuario;
import com.centroinformacion.entity.UsuarioHasRol;
import com.centroinformacion.entity.UsuarioHasRolPK;
import com.centroinformacion.repository.MotivoVisitaRepository;
import com.centroinformacion.repository.UsuarioHasRolRepository;
import com.centroinformacion.repository.UsuarioRepository;
import com.centroinformacion.service.MotivoVisitaService;

import jakarta.transaction.Transactional;

@Service
public class MotivoVisitaServiceImpl implements MotivoVisitaService{
	
	@Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MotivoVisitaRepository motivoVisitaRepository;

    @Autowired
	 private UsuarioHasRolRepository usuarioHasRolRepository;
    
    @Override
    public InvitadoDTO buscarUsuario(String numDoc) {
        Usuario usuario = usuarioRepository.findByNumDoc(numDoc).orElse(null);
        return (usuario != null) ? new InvitadoDTO(usuario) : null;
    }



    @Transactional
    public void registrarUsuarioYMotivo(Usuario usuario, String motivo) {
    	
    	if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo");
        }
    	
        // No buscar nuevamente si el usuario ya fue verificado
        if (usuario.getIdUsuario() == 0) { // Si el usuario no tiene ID, es nuevo
            usuario.setEstado(1); // Estado "ingresado"
            usuario.setLogin("No aplica");
            usuario.setPassword("No aplica"); // Password vacío
            usuario.setFoto("default.jpeg");
            usuario = usuarioRepository.save(usuario);
            
            UsuarioHasRolPK usuarioHasRolPKVisitante = new UsuarioHasRolPK(usuario.getIdUsuario(), 7);
            UsuarioHasRol usuarioHasRolVisitante = new UsuarioHasRol(usuarioHasRolPKVisitante, usuario, new Rol(7)); // Rol de Visitante
            usuarioHasRolRepository.save(usuarioHasRolVisitante); // Guardar la relación en la tabla `usuario_has_rol`
        }

        // Registrar el motivo de visita
        MotivoVisita nuevoMotivo = new MotivoVisita();
        nuevoMotivo.setUsuarioVisitante(usuario);
        nuevoMotivo.setMotivoVisita(motivo);
        nuevoMotivo.setFechaRegistro(LocalDateTime.now());
        motivoVisitaRepository.save(nuevoMotivo);
    }


}
