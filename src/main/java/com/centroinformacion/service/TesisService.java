package com.centroinformacion.service;

import java.util.List;

import com.centroinformacion.entity.Tesis;

public interface TesisService {
	
	//CRUD
	public abstract Tesis insertaActualizaTesis(Tesis obj);
	public abstract List<Tesis> listaTesisPorTituloLike(String titulo);
	public abstract void eliminaTesis(int idTesis);
	public abstract List<Tesis> listaTesis();
	
	//VALIDACION PARA REGISTRAR
	public abstract List<Tesis> listaTesisPorTituloIgualRegistra(String titulo);
	
	//VALIDACION PARA ACTUALIZAR
	public abstract List<Tesis> listaTesisPorTituloIgualActualiza(String titulo, int idTesis);

}