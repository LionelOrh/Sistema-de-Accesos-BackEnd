package com.centroinformacion.security;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;

import com.centroinformacion.entity.Usuario;
import com.centroinformacion.service.UsuarioService;
import com.centroinformacion.util.AppSettings;

@RestController
@RequestMapping("/url/mobile/auth")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class MobileAuthController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUsuario request, HttpSession session) {
        // Autenticación del usuario utilizando el método authenticate del servicio
        Usuario usuario = usuarioService.authenticate(request.getLogin(), request.getPassword());
        
        if (usuario == null) {
            // Retorna error si las credenciales no son válidas
            return ResponseEntity.status(401).body("Credenciales inválidas.");
        }

        // Crear la sesión y almacenar información del usuario
        session.setAttribute("login", usuario.getLogin());
        session.setAttribute("idUsuario", usuario.getIdUsuario());
        session.setAttribute("nombres", usuario.getNombres());
        session.setAttribute("apellidos", usuario.getApellidos());
        session.setAttribute("numDoc", usuario.getNumDoc());
        session.setAttribute("correo", usuario.getCorreo());

        // Retorna la información del usuario en el response
        return ResponseEntity.ok(Map.of(
                "idUsuario", usuario.getIdUsuario(),
                "login", usuario.getLogin(),
                "nombres", usuario.getNombres(),
                "apellidos", usuario.getApellidos(),
                "numDoc", usuario.getNumDoc(),
                "correo", usuario.getCorreo()
        ));
    }
}
