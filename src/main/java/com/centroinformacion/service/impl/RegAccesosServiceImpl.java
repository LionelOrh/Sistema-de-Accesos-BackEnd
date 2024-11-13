package com.centroinformacion.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centroinformacion.dto.MovilRegistroAccesoDTO;
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
	    	// Obtener el estado actual del usuario directamente desde la base de datos
	         String tipoAcceso = obtenerTipoAccesoUsuario(request.getIdUsuario());
	         registro.setTipoAcceso(tipoAcceso);

	         // Actualizar estado directamente
	         usuarioRepository.actualizarEstadoUsuario(request.getIdUsuario());
	         // Asignar referencia del usuario sin cargar toda la entidad
	         registro.setUsuario(new Usuario(request.getIdUsuario()));
	     } else if (request.getIdRepresentante() != null) {
	    	 
	    	// Obtener el estado actual del representante directamente desde la base de datos
	         String tipoAcceso = obtenerTipoAccesoRepresentante(request.getIdRepresentante());
	         registro.setTipoAcceso(tipoAcceso);
	         
	         // Actualizar estado directamente
	         representanteRepository.actualizarEstadoRepresentante(request.getIdRepresentante());
	         // Asignar referencia del representante sin cargar toda la entidad
	         registro.setRepresentante(new Representante(request.getIdRepresentante()));
	     }

	     // Guardar el registro de acceso
	     repository.save(registro);
	 }


	 private String obtenerTipoAccesoRepresentante(Integer idRepresentante) {
		    Integer estado = representanteRepository.obtenerEstadoRepresentante(idRepresentante);
		    return estado == 0 ? "Ingreso" : "Salida";
		}

	 private String obtenerTipoAccesoUsuario(Integer idUsuario) {
		    Integer estado = usuarioRepository.obtenerEstadoUsuario(idUsuario);
		    return estado == 0 ? "Ingreso" : "Salida";
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
			LocalDate fechaAccesoHasta,String numDoc) {
		return repository.listaConsultaCompleja(login, fechaAccesoDesde, fechaAccesoHasta ,numDoc);
	}
	@Override
	public List<RegistroAcceso> listaConsultaCompleta(String numDoc, LocalDate fechaAccesoDesde, LocalDate fechaAccesoHasta) {
		return repository.listaConsultaCompleta(numDoc,fechaAccesoDesde, fechaAccesoHasta);
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

    
    //PARA ACCESOS APP MOVIL
    @Override
	public List<MovilRegistroAccesoDTO> buscarPorUsuario(Integer idUsuario) {
    	List<RegistroAcceso> accesos = repository.findByUsuarioIdUsuario(idUsuario);
        return accesos.stream().map(acceso -> new MovilRegistroAccesoDTO(
                acceso.getFechaAcceso(),
                acceso.getHoraAcceso(),
                acceso.getTipoAcceso())
        ).collect(Collectors.toList());
	}


	@Override
	public List<MovilRegistroAccesoDTO> buscarPorUsuarioYFecha(Integer idUsuario, LocalDate fecha) {
		List<RegistroAcceso> accesos = repository.findByUsuarioIdUsuarioAndFechaAcceso(idUsuario, fecha);
        return accesos.stream().map(acceso -> new MovilRegistroAccesoDTO(
                acceso.getFechaAcceso(),
                acceso.getHoraAcceso(),
                acceso.getTipoAcceso())
        ).collect(Collectors.toList());
    }
	

}