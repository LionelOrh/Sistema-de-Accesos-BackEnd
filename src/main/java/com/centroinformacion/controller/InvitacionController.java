package com.centroinformacion.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.centroinformacion.service.InvitacionService;
import com.centroinformacion.util.AppSettings;

@RestController
@RequestMapping("/url/invitacion")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class InvitacionController {

    @Autowired
    private InvitacionService invitacionService;

 
    @PostMapping("/registraUsuarioInvitado")
    @ResponseBody
    public ResponseEntity<Map<String, String>> registrarInvitado(@RequestBody Map<String, Object> payload) {
        Map<String, String> respuesta = invitacionService.registrarUsuarioEInvitacion(payload);
        return ResponseEntity.ok(respuesta);
    }

}
