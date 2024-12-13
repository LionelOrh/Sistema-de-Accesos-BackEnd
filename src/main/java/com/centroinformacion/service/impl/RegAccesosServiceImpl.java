package com.centroinformacion.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centroinformacion.dto.PreRegistroConsultaDTO;
import com.centroinformacion.dto.RegistroAccesoIEDTO;
import com.centroinformacion.dto.RegistroAccesoRepresentanteDTO;
import com.centroinformacion.dto.RegistroRequest;
import com.centroinformacion.entity.Invitacion; // Asegúrate de importar la entidad Invitacion
import com.centroinformacion.entity.MotivoVisita;
import com.centroinformacion.entity.RegistroAcceso;
import com.centroinformacion.entity.Representante;
import com.centroinformacion.entity.Usuario;
import com.centroinformacion.repository.InvitacionRepository; // Importar el repositorio de Invitacion
import com.centroinformacion.repository.MotivoVisitaRepository;
import com.centroinformacion.repository.RegAccesosRepository;
import com.centroinformacion.repository.RepresentanteRepository;
import com.centroinformacion.repository.UsuarioRepository;
import com.centroinformacion.service.RegAccesosService;

@Service
public class RegAccesosServiceImpl implements RegAccesosService {

    @Autowired
    private RegAccesosRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RepresentanteRepository representanteRepository;

    @Autowired
    private InvitacionRepository invitacionRepository; // Inyección del repositorio de Invitacion
    @Autowired
    private MotivoVisitaRepository motivovisitaRepository; // Inyección del repositorio de Invitacion
  
    @Transactional
    @Override
    public void registrarAcceso(RegistroRequest request) {
        Usuario usuarioRegistrador = usuarioRepository.findById(request.getIdUsuarioRegAcceso())
            .orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

        RegistroAcceso registro = new RegistroAcceso();
        registro.setFechaAcceso(LocalDate.now());
        registro.setHoraAcceso(LocalTime.now());
        registro.setUsuarioRegAcceso(usuarioRegistrador);

        if (request.getIdUsuario() != null) {
            String tipoAcceso = obtenerTipoAccesoUsuario(request.getIdUsuario());
            registro.setTipoAcceso(tipoAcceso);

            usuarioRepository.actualizarEstadoUsuario(request.getIdUsuario());
            registro.setUsuario(new Usuario(request.getIdUsuario()));
        } else if (request.getIdRepresentante() != null) {
            String tipoAcceso = obtenerTipoAccesoRepresentante(request.getIdRepresentante());
            registro.setTipoAcceso(tipoAcceso);

            representanteRepository.actualizarEstadoRepresentante(request.getIdRepresentante());
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
    public List<RegistroAccesoIEDTO> listaConsultaCompleja(String loginOrNumDoc, LocalDate fechaAccesoDesde,
            LocalDate fechaAccesoHasta) {
        return repository.listaConsultaCompleja(loginOrNumDoc, fechaAccesoDesde, fechaAccesoHasta);
    }

    @Override
    public List<RegistroAccesoRepresentanteDTO>listaConsultaCompleta(String numDoc, LocalDate fechaInicio, LocalDate fechaFin) {
        return repository.listaConsultaCompleta(numDoc, fechaInicio, fechaFin);
    }

    @Override
    public PreRegistroConsultaDTO buscarPorCodigo(String codigo) {
        Optional<Usuario> usuario = usuarioRepository.buscarPorCodigo(codigo).stream().findFirst();
        if (usuario.isPresent()) {
            PreRegistroConsultaDTO dto = crearDTODesdeUsuario(usuario.get());
            añadirMotivo(dto, Long.valueOf(usuario.get().getIdUsuario())); // Asegúrate de convertir a Long
            return dto;
        }
        
        Optional<Representante> representante = representanteRepository.buscarPorCodigo(codigo).stream().findFirst();
        if (representante.isPresent()) {
            PreRegistroConsultaDTO dto = crearDTODesdeRepresentante(representante.get());
            añadirMotivo(dto, Long.valueOf(representante.get().getIdRepresentante())); // Conversión a Long
            return dto;
        }
        return null;
    }
    private void añadirMotivo(PreRegistroConsultaDTO dto, Long idUsuario) {
        Optional<Invitacion> invitacion = invitacionRepository.findByUsuarioInvitado_IdUsuario(idUsuario);
        if (invitacion.isPresent()) {
            dto.setMotivo(invitacion.get().getMotivo());
        } else {
            Optional<MotivoVisita> motivoVisita = motivovisitaRepository.findByUsuarioVisitante_IdUsuario(idUsuario);
            if (motivoVisita.isPresent()) {
                dto.setMotivoVisita(motivoVisita.get().getMotivoVisita());
            } else {
                dto.setMotivo("No aplica");
                dto.setMotivoVisita("No aplica");
            }
        }


    }

    private PreRegistroConsultaDTO crearDTODesdeUsuario(Usuario usuario) {
        PreRegistroConsultaDTO dto = new PreRegistroConsultaDTO();
        dto.setCodigo(usuario.getLogin() != null ? usuario.getLogin() : "no aplica");
        dto.setIdentificacion(usuario.getNumDoc());
        dto.setNombres(usuario.getNombres());
        dto.setApellidos(usuario.getApellidos());
        dto.setEstado(usuario.getEstado() == 1 ? "Dentro de la Institución" : "No se encuentra en la Institución");
        if (usuario.getFoto() != null && !usuario.getFoto().isEmpty()) {
            dto.setFoto("http://localhost:8090/uploads?filename=fotos/" + usuario.getFoto());
        } else {
            dto.setFoto(null);
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

    // PARA ACCESOS APP MOVIL
    public List<RegistroAcceso> obtenerAccesosFiltrados(Integer idUsuario, Optional<LocalDate> fecha) {
        if (fecha.isPresent()) {
            return repository.findByUsuario_IdUsuarioAndFechaAcceso(idUsuario, fecha.get());
        } else {
            return repository.findByUsuario_IdUsuario(idUsuario);
        }
    }
}