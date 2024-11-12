package com.centroinformacion.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.apachecommons.CommonsLog;

@Component
@CommonsLog
public class JwtEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException e)
	        throws IOException, ServletException {

	    String path = req.getRequestURI();
	    log.info("JwtEntryPoint>> Solicitud para: " + path);

	    // Ignorar rutas públicas (como /uploads/**) y errores generados por recursos no encontrados
	    if (path.startsWith("/uploads/") || path.equals("/error")) {
	        log.info("JwtEntryPoint>> Ruta pública o de error detectada, permitiendo acceso: " + path);
	        res.setStatus(HttpServletResponse.SC_OK); // Permitir acceso
	        return;
	    }

	    // Manejo normal para rutas protegidas
	    log.error("JwtEntryPoint>> SC_UNAUTHORIZED >> no autorizado");
	    res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Usuario no Autorizado");
	}

}
