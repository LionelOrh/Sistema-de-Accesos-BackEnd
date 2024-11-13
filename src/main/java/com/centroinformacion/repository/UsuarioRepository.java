package com.centroinformacion.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.centroinformacion.entity.Opcion;
import com.centroinformacion.entity.Rol;
import com.centroinformacion.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

	@Query("Select x from Usuario x where x.login = :#{#usu.login} and x.password = :#{#usu.password}")
	public abstract Usuario login(@Param(value = "usu") Usuario bean);
	
	@Query("Select p from Opcion p, RolHasOpcion pr, Rol r, UsuarioHasRol u where  p.idOpcion = pr.opcion.idOpcion and pr.rol.idRol = r.idRol and r.idRol = u.rol.idRol and u.usuario.idUsuario = :var_idUsuario")
	public abstract List<Opcion> traerEnlacesDeUsuario(@Param("var_idUsuario") int idUsuario);

	@Query("Select r from Rol r, UsuarioHasRol u where r.idRol = u.rol.idRol and u.usuario.idUsuario = :var_idUsuario")
	public abstract List<Rol> traerRolesDeUsuario(@Param("var_idUsuario")int idUsuario);
	
	public abstract Usuario findByLogin(String login);
	
	 Optional<Usuario> findById(int idUsuario);
	 
	 Usuario findByLoginAndPassword(String login, String password);
	 
	 Usuario findByNumDoc(String numDoc);
	 
	 
	 @Query("SELECT u FROM Usuario u WHERE u.login = :codigo OR u.numDoc = :codigo")
	 List<Usuario> buscarPorCodigo(@Param("codigo") String codigo);
	 
	 @Modifying
	 @Query("UPDATE Usuario u SET u.estado = CASE WHEN u.estado = 0 THEN 1 ELSE 0 END WHERE u.idUsuario = :idUsuario")
	 void actualizarEstadoUsuario(@Param("idUsuario") Integer idUsuario);


}
