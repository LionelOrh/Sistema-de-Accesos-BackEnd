package com.centroinformacion.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.centroinformacion.entity.MotivoVisita;
import com.centroinformacion.entity.Rol;
import com.centroinformacion.entity.Usuario;
import com.centroinformacion.entity.UsuarioHasRol;
import com.centroinformacion.entity.UsuarioHasRolPK;
import com.centroinformacion.entity.VisitanteRequest;
import com.centroinformacion.repository.MotivoVisitaRepository;
import com.centroinformacion.repository.UsuarioHasRolRepository;
import com.centroinformacion.repository.UsuarioRepository;
import com.centroinformacion.service.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.core.io.FileSystemResource; // Para acceder a archivos en el sistema de archivos
import org.springframework.core.io.Resource;          // Interfaz para recursos (usado por FileSystemResource)
import org.springframework.http.HttpStatus;          // Para manejar los códigos de estado HTTP
import org.springframework.http.MediaType;           // Para definir el tipo de contenido de la respuesta
import org.springframework.http.ResponseEntity;      // Para construir la respuesta con el archivo de imagen
import org.springframework.web.bind.annotation.GetMapping; // Para manejar las solicitudes GET
import org.springframework.web.bind.annotation.PathVariable; // Para acceder a las variables en la URL
import java.nio.file.Path;  // Para trabajar con rutas de archivos
import java.nio.file.Paths; // Para crear rutas a archivos


@RestController
@RequestMapping("/url/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;  
    
    @Autowired
    private UsuarioRepository usuarioRepository; 
    
    @Autowired
    private MotivoVisitaRepository motivoVisitaRepository; 
    
    @Autowired
    private UsuarioHasRolRepository usuarioHasRolRepository; 

    @GetMapping("/buscarPorId")
    public ResponseEntity<?> obtenerUsuarioPorId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return ResponseEntity.status(401).body("Usuario no autenticado.");
        }

        int idUsuario = (int) session.getAttribute("idUsuario");
        Usuario usuario = usuarioService.buscaPorId(idUsuario);  
        if (usuario == null) {
            return ResponseEntity.status(404).body("Usuario no encontrado.");
        }
        return ResponseEntity.ok(usuario);
    }
    
    @PostMapping("/registrarVisitante")
    public ResponseEntity<String> registrarVisitante(@RequestBody VisitanteRequest visitanteRequest) {
        try {
            // Crear y guardar el Usuario
            Usuario usuario = visitanteRequest.getUsuario();
            usuarioRepository.save(usuario);

            // Crear el MotivoVisita y asociarlo con el Usuario
            MotivoVisita motivoVisita = new MotivoVisita();
            motivoVisita.setMotivoVisita(visitanteRequest.getMotivoVisita());
            motivoVisita.setUsuarioVisitante(usuario);
            motivoVisita.setFechaRegistro(new Date());

            motivoVisitaRepository.save(motivoVisita);

            // Registrar el rol del usuario en usuario_tiene_rol (idRol = 8)
            UsuarioHasRolPK usuarioHasRolPK = new UsuarioHasRolPK(usuario.getIdUsuario(), 8);
            UsuarioHasRol usuarioHasRol = new UsuarioHasRol(usuarioHasRolPK, usuario, new Rol(8)); // Asumiendo que `new Rol(8)` crea una instancia con el rol 8

            usuarioHasRolRepository.save(usuarioHasRol);

            return new ResponseEntity<>("Visitante registrado exitosamente con rol asignado", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al registrar visitante: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/registrarMotivoVisita")
    public ResponseEntity<String> registrarMotivoVisita(@RequestBody VisitanteRequest visitanteRequest) {
        try {
            // Buscar el usuario por su número de documento
            Usuario usuario = usuarioService.buscarPorNumeroDocumento(visitanteRequest.getUsuario().getNumDoc());
            
            if (usuario == null) {
                return new ResponseEntity<>("Usuario no encontrado.", HttpStatus.NOT_FOUND);
            }
            
            // Registrar el motivo de visita en la tabla MotivoVisita
            MotivoVisita motivoVisita = new MotivoVisita();
            motivoVisita.setMotivoVisita(visitanteRequest.getMotivoVisita());
            motivoVisita.setUsuarioVisitante(usuario);
            motivoVisita.setFechaRegistro(new Date());
            
            motivoVisitaRepository.save(motivoVisita);

            return new ResponseEntity<>("Motivo de visita registrado exitosamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("No se encontró tu número de documento: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<Resource> obtenerFoto(@PathVariable int idUsuario) {
        // Buscar al usuario
        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);

        // Si el usuario existe y tiene una foto
        if (usuario != null && usuario.getFoto() != null) {
            // Suponiendo que 'usuario.getFoto()' devuelve el nombre del archivo de la imagen
            String fotoRuta = "uploads/fotos/" + usuario.getFoto();  // Concatenamos el path base

            Path path = Paths.get(fotoRuta);
            Resource resource = new FileSystemResource(path);  // Obtenemos el archivo de la imagen

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)  // Puedes ajustar el tipo de imagen según lo que sea
                        .body(resource);  // Devolvemos el archivo de la imagen
            }
        }
        
        // Si no se encuentra la foto, devolvemos un error
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }


}