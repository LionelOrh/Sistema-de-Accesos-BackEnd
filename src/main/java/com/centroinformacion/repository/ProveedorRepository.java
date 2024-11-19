package com.centroinformacion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.centroinformacion.entity.Proveedor;

public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {
	List<Proveedor> findTop5ByOrderByIdProveedorAsc();

	List<Proveedor> findTop5ByRazonSocialContaining(String razonSocial);
	
	boolean existsByRuc(String ruc);

	boolean existsByRazonSocial(String razonSocial);
}
