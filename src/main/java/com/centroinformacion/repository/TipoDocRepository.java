package com.centroinformacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.centroinformacion.entity.TipoDocumento;

public interface TipoDocRepository extends JpaRepository<TipoDocumento, Integer>{

}