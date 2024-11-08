package com.centroinformacion.service;

import java.util.List;

import com.centroinformacion.entity.TipoDocumento;

public interface TipoDocService {
	
	public abstract List<TipoDocumento> listaTodos();

	// Método para obtener un TipoDocumento por su ID
    public abstract TipoDocumento obtenerTipoDocumentoPorId(Integer idTipoDoc);
}
