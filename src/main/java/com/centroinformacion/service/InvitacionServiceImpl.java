package com.centroinformacion.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centroinformacion.entity.Invitacion;
import com.centroinformacion.entity.TipoDocumento;
import com.centroinformacion.entity.Usuario;
import com.centroinformacion.entity.UsuarioHasRol;
import com.centroinformacion.entity.UsuarioHasRolPK;
import com.centroinformacion.repository.InvitacionRepository;
import com.centroinformacion.repository.UsuarioHasRolRepository;
import com.centroinformacion.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class InvitacionServiceImpl implements InvitacionService{
	
	@Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioHasRolRepository usuarioHasRolRepository;

    @Autowired
    private InvitacionRepository invitacionRepository;

    @Transactional
    @Override
    public Map<String, String> registrarUsuarioEInvitacion(Map<String, Object> payload) {
        Map<String, String> salida = new HashMap<>();

        try {
            // Extraer y procesar datos del payload
            Map<String, Object> tipoDocMap = (Map<String, Object>) payload.get("tipodocumento");
            TipoDocumento tipoDoc = new TipoDocumento();
            tipoDoc.setIdTipoDoc(Integer.parseInt(tipoDocMap.get("idTipoDoc").toString()));

            // Crear y guardar el usuario
            Usuario usuario = new Usuario();
            usuario.setNombres((String) payload.get("nombres"));
            usuario.setApellidos((String) payload.get("apellidos"));
            usuario.setCelular((String) payload.get("celular"));
            usuario.setCorreo((String) payload.get("correo"));
            usuario.setNumDoc((String) payload.get("numDoc"));
            usuario.setLogin("No aplica");
            usuario.setPassword("No aplica");
            usuario.setFoto("No aplica");
            usuario.setTipodocumento(tipoDoc);
            usuario.setEstado(0);

            Usuario nuevoUsuario = usuarioRepository.save(usuario);

            // Asignar el rol al usuario
            UsuarioHasRolPK usuarioHasRolPK = new UsuarioHasRolPK(nuevoUsuario.getIdUsuario(), 7); // Rol de invitado
            UsuarioHasRol usuarioHasRol = new UsuarioHasRol(usuarioHasRolPK, nuevoUsuario, null);
            usuarioHasRolRepository.save(usuarioHasRol);

            // Crear y guardar la invitación
            Invitacion invitacion = new Invitacion();
            invitacion.setMotivo((String) payload.get("motivo"));
            invitacion.setFechaInvitacion(new Date());

            Integer idUsuarioRegVisita = Integer.parseInt(payload.get("idUsuarioRegVisita").toString());
            Usuario usuarioRegVisita = usuarioRepository.findById(idUsuarioRegVisita).orElseThrow(() -> 
                new IllegalArgumentException("Usuario registrador no encontrado"));
            invitacion.setUsuarioRegVisita(usuarioRegVisita);
            invitacion.setUsuarioInvitado(nuevoUsuario);

            Invitacion invitacionGuardada = invitacionRepository.save(invitacion);

            // Preparar mensaje de respuesta
            salida.put("mensaje", "Se registró la invitación con el ID ==> " + invitacionGuardada.getIdInvitacion());
            salida.put("mensajeUsuario", "Se registró al usuario con el ID ==> " + nuevoUsuario.getIdUsuario());
        } catch (Exception e) {
            throw new RuntimeException("Error en el registro de la invitación: " + e.getMessage(), e);
        }

        return salida;
    }

	@Override
	public Invitacion guardaInvitacion(Invitacion obj) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}