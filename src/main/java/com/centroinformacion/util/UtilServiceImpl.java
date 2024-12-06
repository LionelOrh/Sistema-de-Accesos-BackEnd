package com.centroinformacion.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centroinformacion.repository.ProveedorRepository;
import com.centroinformacion.repository.RepresentanteRepository;
import com.centroinformacion.repository.UsuarioRepository;

@Service
public class UtilServiceImpl implements UtilService{
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private RepresentanteRepository representanteRepository;
	
	@Autowired
	private ProveedorRepository proveedorRepository;
	
	@Override
	public boolean validarNumDoc(String numDoc) {
	    // Verifica si el numDoc existe en Usuario o Representante
	    boolean existeEnUsuario = usuarioRepository.existsByNumDoc(numDoc);
	    boolean existeEnRepresentante = representanteRepository.existsByNumDoc(numDoc);
	    return existeEnUsuario || existeEnRepresentante;
	}
	
	@Override
	public boolean validarNumDocUsuario(String numDoc) {
	    // Verifica si el numDoc existe en Usuario
	    boolean existeEnUsuario = usuarioRepository.existsByNumDoc(numDoc);
	    return existeEnUsuario;
	}
	
	@Override
	public boolean validarNumDocRepresentante(String numDoc) {
	    // Verifica si el numDoc existe en Representante
		 boolean existeEnRepresentante = representanteRepository.existsByNumDoc(numDoc);
	    return existeEnRepresentante;
	}

	@Override
	public boolean validarRuc(String ruc) {
		boolean existeRucEnProveedor = proveedorRepository.existsByRuc(ruc);
		return existeRucEnProveedor;
	}

	@Override
	public boolean validarRazonSocial(String razonSocial) {
		boolean existeRazonSocialProveedor = proveedorRepository.existsByRazonSocial(razonSocial);
		return existeRazonSocialProveedor;
	}
}
