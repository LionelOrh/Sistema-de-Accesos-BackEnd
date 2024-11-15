package com.centroinformacion.service;

import java.util.List;

import com.centroinformacion.entity.Proveedor;

public interface  ProveedorService {
	
	List<Proveedor> consultarProveedores(String filtro);
	
	Proveedor registrarProveedor(Proveedor proveedor);
}
