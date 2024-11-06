package com.centroinformacion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centroinformacion.entity.MotivoVisita;
import com.centroinformacion.repository.MotivoVisitaRepository;

@Service
public class MotivoVisitaServiceImpl implements MotivoVisitaService {

	@Autowired
    private MotivoVisitaRepository motivoVisitaRepository;
	
	@Override
	 public void registrarMotivoVisita(MotivoVisita motivoVisita) {
        motivoVisitaRepository.save(motivoVisita);
    }

}
