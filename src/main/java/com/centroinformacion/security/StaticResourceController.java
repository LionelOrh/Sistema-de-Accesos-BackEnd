package com.centroinformacion.security;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class StaticResourceController {

	@GetMapping("/uploads")
	public ResponseEntity<Resource> getFile(@RequestParam String filename) {
	    try {
	        Path file = Paths.get("uploads").resolve(filename);
	        Resource resource = new UrlResource(file.toUri());

	        if (resource.exists() || resource.isReadable()) {
	            // Detectar el tipo MIME
	            String contentType = Files.probeContentType(file);
	            if (contentType == null || !contentType.startsWith("image")) {
	                // Asegurar que sea un tipo MIME válido para imágenes
	                contentType = "image/jpeg";
	            }

	            // Establecer encabezados correctos
	            return ResponseEntity.ok()
	                    .header("Content-Type", contentType)
	                    .body(resource);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

}
