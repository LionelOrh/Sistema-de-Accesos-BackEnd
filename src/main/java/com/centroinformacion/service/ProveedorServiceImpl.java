package com.centroinformacion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centroinformacion.entity.Proveedor;
import com.centroinformacion.repository.ProveedorRepository;



@Service
public class ProveedorServiceImpl implements ProveedorService{
	@Autowired
    private ProveedorRepository repository;

    @Override
    public List<Proveedor> consultarProveedores(String filtro) {
        if (filtro != null && !filtro.isEmpty()) {
            return repository.findTop5ByRazonSocialContaining(filtro);
        }
        return repository.findTop5ByOrderByIdProveedorAsc();
    }

	@Override
	public Proveedor registrarProveedor(Proveedor proveedor) {
		return repository.save(proveedor);
	}
}
