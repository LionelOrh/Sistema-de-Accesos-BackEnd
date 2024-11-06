package com.centroinformacion.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centroinformacion.entity.MotivoVisita;
import com.centroinformacion.entity.Opcion;
import com.centroinformacion.entity.Rol;
import com.centroinformacion.entity.TipoDocumento;
import com.centroinformacion.entity.Usuario;
import com.centroinformacion.entity.UsuarioHasRol;
import com.centroinformacion.entity.UsuarioHasRolPK;
import com.centroinformacion.repository.MotivoVisitaRepository;
import com.centroinformacion.repository.RolRepository;
import com.centroinformacion.repository.TipoDocRepository;
import com.centroinformacion.repository.UsuarioHasRolRepository;
import com.centroinformacion.repository.UsuarioRepository;


@Service
public class UsuarioServiceImpl implements UsuarioService{

	@Autowired
	private UsuarioRepository repository;
	
	 @Autowired
	 private RolRepository rolRepository;

	 @Autowired
	 private MotivoVisitaRepository motivoVisitaRepository;

	 @Autowired
	 private UsuarioHasRolRepository usuarioHasRolRepository;
	 
	 @Autowired
	 private TipoDocRepository tipoDocumentoRepository;
	 
	
	@Override
	public List<Opcion> traerEnlacesDeUsuario(int idUsuario) {
		return repository.traerEnlacesDeUsuario(idUsuario);
	}

	@Override
	public List<Rol> traerRolesDeUsuario(int idUsuario) {
		return repository.traerRolesDeUsuario(idUsuario);
	}

	@Override
	public Usuario buscaPorLogin(String login) {
		return repository.findByLogin(login);
	}

	@Override
	public Usuario buscaPorId(int idUsuario) {
		return repository.findById(idUsuario).orElse(null);
	}

	@Override
	public Usuario authenticate(String login, String password) {
		 return repository.findByLoginAndPassword(login, password);
	}

	 @Override
	    public void registrarVisitante(String nombres, String apellidos, String celular, String correo, String numDoc, String motivoVisita, int idTipoDoc) {
	        // 1. Insertar nuevo usuario (Visitante)
	        Usuario usuario = new Usuario();
	        usuario.setNombres(nombres);
	        usuario.setApellidos(apellidos);
	        usuario.setCelular(celular);
	        usuario.setCorreo(correo);
	        usuario.setNumDoc(numDoc);
	        
	        // Establecer el tipo de documento con la entidad `TipoDocumento`
	        TipoDocumento tipoDocumento = new TipoDocumento();
	        tipoDocumento.setIdTipoDoc(idTipoDoc);  // ID recibido del formulario (en este caso, del combo)
	        usuario.setTipodocumento(tipoDocumento);  // Asignamos la entidad tipoDocumento a usuario
	        
	        repository.save(usuario);  // Guardar el usuario en la base de datos

	        // 2. Asignar el rol de Visitante (ID 8 corresponde al rol de Visitante)
	        Rol rol = rolRepository.findByIdRol(8);  // El idRol 8 corresponde al rol de Visitante
	        UsuarioHasRolPK usuarioHasRolPK = new UsuarioHasRolPK(usuario.getIdUsuario(), rol.getIdRol());
	        UsuarioHasRol usuarioHasRol = new UsuarioHasRol(usuarioHasRolPK, usuario, rol);
	        usuarioHasRolRepository.save(usuarioHasRol);  // Guardar la relación en la tabla `usuario_has_rol`

	        // 3. Registrar el motivo de visita en la tabla `MotivoVisita`
	        MotivoVisita motivo = new MotivoVisita();
	        motivo.setUsuarioVisitante(usuario);
	        motivo.setMotivoVisita(motivoVisita);
	        motivo.setFechaRegistro(new Date());  // Fecha actual
	        motivoVisitaRepository.save(motivo);  // Guardar el motivo de visita en la tabla `MotivoVisita`
	    }

}
