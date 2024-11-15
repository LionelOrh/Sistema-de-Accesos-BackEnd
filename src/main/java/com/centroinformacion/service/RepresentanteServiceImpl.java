package com.centroinformacion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centroinformacion.entity.Representante;
import com.centroinformacion.repository.RepresentanteRepository;

@Service
public class RepresentanteServiceImpl implements RepresentanteService {

    @Autowired
    private RepresentanteRepository representanteRepository;

    @Override
    public void registroRepresentante(Representante representante) {
        representanteRepository.save(representante);
    }
}
