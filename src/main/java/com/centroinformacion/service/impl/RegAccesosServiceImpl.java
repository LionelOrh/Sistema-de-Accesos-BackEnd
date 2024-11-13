package com.centroinformacion.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centroinformacion.dto.PreRegistroConsultaDTO;
import com.centroinformacion.dto.RegistroRequest;
import com.centroinformacion.entity.RegistroAcceso;
import com.centroinformacion.entity.Representante;
import com.centroinformacion.entity.Usuario;
import com.centroinformacion.repository.RegAccesosRepository;
import com.centroinformacion.repository.RepresentanteRepository;
import com.centroinformacion.repository.UsuarioRepository;
import com.centroinformacion.service.RegAccesosService;

@Service
public class RegAccesosServiceImpl implements RegAccesosService{

	 @Autowired
	 private RegAccesosRepository repository;
	 
	 @Transactional
	 @Override
	 public void registrarAcceso(RegistroRequest request) {
	     // Validar el usuario que realiza el registro
	     Usuario usuarioRegistrador = usuarioRepository.findById(request.getIdUsuarioRegAcceso())
	         .orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

	     // Crear el registro de acceso
	     RegistroAcceso registro = new RegistroAcceso();
	     registro.setFechaAcceso(LocalDate.now());
	     registro.setHoraAcceso(LocalTime.now());
	     registro.setUsuarioRegAcceso(usuarioRegistrador);

	     if (request.getIdUsuario() != null) {
	         // Actualizar estado directamente
	         usuarioRepository.actualizarEstadoUsuario(request.getIdUsuario());
	         // Asignar referencia del usuario sin cargar toda la entidad
	         registro.setUsuario(new Usuario(request.getIdUsuario()));
	     } else if (request.getIdRepresentante() != null) {
	         // Actualizar estado directamente
	         representanteRepository.actualizarEstadoRepresentante(request.getIdRepresentante());
	         // Asignar referencia del representante sin cargar toda la entidad
	         registro.setRepresentante(new Representante(request.getIdRepresentante()));
	     }

	     // Guardar el registro de acceso
	     repository.save(registro);
	 }






	@Override
	public List<RegistroAcceso> listaPorLogin(String login) {
		  return repository.findByUsuario_Login(login);
	}

	@Override
	public List<RegistroAcceso> listaPorFechaAcceso(LocalDate fechaAcceso) {
		  return repository.findByFechaAcceso(fechaAcceso);
	}

	@Override
	public List<RegistroAcceso> listaConsultaCompleja(String login, LocalDate fechaAccesoDesde,
			LocalDate fechaAccesoHasta, int idTipoAcceso,String numDoc) {
		return repository.listaConsultaCompleja(login, fechaAccesoDesde, fechaAccesoHasta, idTipoAcceso,numDoc);
	}
	@Override
	public List<RegistroAcceso> listaConsultaCompleta(String numDoc) {
		return repository.listaConsultaCompleta(numDoc);
	}
	//Service para la consulta
	@Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RepresentanteRepository representanteRepository;

    @Override
    public PreRegistroConsultaDTO buscarPorCodigo(String codigo) {
        // 1. Buscar en Usuario
        Optional<Usuario> usuario = usuarioRepository.buscarPorCodigo(codigo).stream().findFirst();
        if (usuario.isPresent()) {
            return crearDTODesdeUsuario(usuario.get());
        }

        // 2. Buscar en Representante
        Optional<Representante> representante = representanteRepository.buscarPorCodigo(codigo).stream().findFirst();
        if (representante.isPresent()) {
            return crearDTODesdeRepresentante(representante.get());
        }

        // 3. Si no se encuentra
        return null; // O lanzar una excepción personalizada
    }

    private PreRegistroConsultaDTO crearDTODesdeUsuario(Usuario usuario) {
        PreRegistroConsultaDTO dto = new PreRegistroConsultaDTO();
        dto.setCodigo(usuario.getLogin() != null ? usuario.getLogin() : "no aplica");
        dto.setIdentificacion(usuario.getNumDoc());
        dto.setNombres(usuario.getNombres());
        dto.setApellidos(usuario.getApellidos());
        dto.setEstado(usuario.getEstado() == 1 ? "Ingreso" : "Salida");
     // Ajustar la URL completa para la foto
        if (usuario.getFoto() != null && !usuario.getFoto().isEmpty()) {
            dto.setFoto("http://localhost:8090/uploads?filename=fotos/" + usuario.getFoto());
        } else {
            dto.setFoto(null); // Si no hay foto, dejar null
        }
        dto.setId(usuario.getIdUsuario());
        dto.setTipo("usuario");
        return dto;
    }

    private PreRegistroConsultaDTO crearDTODesdeRepresentante(Representante representante) {
        PreRegistroConsultaDTO dto = new PreRegistroConsultaDTO();
        dto.setCodigo("no aplica");
        dto.setIdentificacion(representante.getNumDoc());
        dto.setNombres(representante.getNombres());
        dto.setApellidos(representante.getApellidos());
        dto.setEstado(representante.getEstado() == 1 ? "Ingreso" : "Salida");
        dto.setId(representante.getIdRepresentante());
        dto.setTipo("representante");
        return dto;
    }

}