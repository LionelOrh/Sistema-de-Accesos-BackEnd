package com.centroinformacion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centroinformacion.entity.Autor;
import com.centroinformacion.repository.AutorRepository;

@Service
public class AutorServiceImp implements AutorService {

	@Autowired	
	private AutorRepository repository;

	@Override
	public Autor insertaActualizaAutor(Autor obj) {
		return repository.save(obj);
	}
	
	@Override
	public List<Autor> listaAutor() {
		return repository.findAll();
	}

	@Override
	public List<Autor> listaAutorPorNombresLike(String nombres) {
		return repository.listaPorNombresLike(nombres);
	}

	@Override
	public void eliminaAutor(int idAutor) {
		repository.deleteById(idAutor);
		
	}

	@Override
    public List<Autor> listaAutorPorNombresIgualRegistra(String nombres) {
        return repository.listaPorNombresIgualRegistra(nombres);
    }
    
    @Override
    public List<Autor> listaAutorPorApellidosIgualRegistra(String apellidos) {
        return repository.listaPorApellidosIgualRegistra(apellidos);
    }

    @Override
    public List<Autor> listaAutorPorCelularIgualRegistra(String celular) {
        return repository.listaPorCelularIgualRegistra(celular);
    }

	@Override
	public List<Autor> listaAutorPorNombresIgualActualiza(String nombres, int idAutor) {
		return repository.listaPorNombresIgualActualiza(nombres, idAutor);
	}
	
	@Override
    public List<Autor> listaAutorPorTelefonoIgualRegistra(String telefono) {
        return repository.listaAutorPorTelefonoIgualRegistra(telefono);
    }

	@Override
	public List<Autor> listaAutorPorApellidosIgualActualiza(String apellidos, int idAutor) {
		 return repository.listaPorApellidosIgualActualiza(apellidos, idAutor);
	}

	@Override
	public List<Autor> listaAutorPorCelularIgualActualiza(String celular, int idAutor) {
		 return repository.listaPorCelularIgualActualiz(celular, idAutor);
	}

	@Override
	public List<Autor> listaAutorPorTelefonoIgualActualiza(String telefono, int idAutor) {
		 return repository.listaAutorPorTelefonoIgualActualiza(telefono, idAutor);
	}

	
	
}