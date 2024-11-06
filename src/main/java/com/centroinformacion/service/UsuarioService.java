package com.centroinformacion.service;

import java.util.List;

import com.centroinformacion.entity.Opcion;
import com.centroinformacion.entity.Rol;
import com.centroinformacion.entity.Usuario;
import com.centroinformacion.entity.UsuarioHasRol;

public interface UsuarioService {

	public abstract List<Opcion> traerEnlacesDeUsuario(int idUsuario);

	public abstract List<Rol> traerRolesDeUsuario(int idUsuario);

	public abstract Usuario buscaPorLogin(String login);

	public abstract Usuario buscaPorId(int idUsuario);
	
	public abstract Usuario authenticate(String login, String password);
	
	public Usuario guardarUsuario(Usuario usuario);
	
	public void asignarRol(UsuarioHasRol usuarioHasRol);

}
