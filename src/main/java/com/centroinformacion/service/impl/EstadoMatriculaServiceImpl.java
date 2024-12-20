package com.centroinformacion.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centroinformacion.entity.EstadoMatricula;
import com.centroinformacion.repository.EstadoMatriculaRepository;
import com.centroinformacion.service.EstadoMatriculaService;

@Service
public class EstadoMatriculaServiceImpl implements EstadoMatriculaService{

	@Autowired
    private EstadoMatriculaRepository estadoMatriculaRepository;

    public boolean verificarMatricula(Integer idUsuario) {
        EstadoMatricula estadoMatricula = estadoMatriculaRepository.findByUsuarioIdUsuario(idUsuario);
        return estadoMatricula != null && estadoMatricula.isEstado();
    }

}
