package com.centroinformacion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centroinformacion.entity.Opcion;
import com.centroinformacion.entity.Rol;
import com.centroinformacion.entity.Usuario;
import com.centroinformacion.entity.UsuarioHasRol;
import com.centroinformacion.repository.UsuarioHasRolRepository;
import com.centroinformacion.repository.UsuarioRepository;


@Service
public class UsuarioServiceImpl implements UsuarioService{

	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private UsuarioHasRolRepository usuarioHasRolRepository;
	
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
	public Usuario guardarUsuario(Usuario usuario) {
		return repository.save(usuario);
	}

	@Override
	public void asignarRol(UsuarioHasRol usuarioHasRol) {
		usuarioHasRolRepository.save(usuarioHasRol);
	}

}
