package com.centroinformacion.service;

import java.util.List;

import com.centroinformacion.entity.Autor;

public interface AutorService {

	// Para el crud
	public abstract Autor insertaActualizaAutor(Autor obj);
	public abstract List<Autor> listaAutorPorNombresLike(String nombres);
	public abstract void eliminaAutor(int idAutor);
	public abstract List<Autor> listaAutor();

	// Validaciones Para Registrar
	List<Autor> listaAutorPorNombresIgualRegistra(String nombres);
    List<Autor> listaAutorPorApellidosIgualRegistra(String apellidos);
    List<Autor> listaAutorPorCelularIgualRegistra(String celular);
    List<Autor> listaAutorPorTelefonoIgualRegistra(String telefono);

	// Validaciones Para Actualizar
	List<Autor> listaAutorPorNombresIgualActualiza(String nombres, int idAutor);
	List<Autor> listaAutorPorApellidosIgualActualiza(String apellidos,  int idAutor);
    List<Autor> listaAutorPorCelularIgualActualiza(String celular,  int idAutor);
    List<Autor> listaAutorPorTelefonoIgualActualiza(String telefono,  int idAutor);

}