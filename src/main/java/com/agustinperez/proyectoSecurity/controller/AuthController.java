package com.agustinperez.proyectoSecurity.controller;

import com.agustinperez.proyectoSecurity.dto.JwtResponse;
import com.agustinperez.proyectoSecurity.dto.LoginRequest;
import com.agustinperez.proyectoSecurity.dto.RegisterRequest;
import com.agustinperez.proyectoSecurity.service.AuthService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    private static final Logger log =
            LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginRequest request) {
        log.info("Solicitud de login recibida");

        JwtResponse response = authService.login(request);

        log.info("Login procesado correctamente");

        return response;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody RegisterRequest request) {

        log.info("Solicitud de registro recibida");

        authService.register(request);

        log.info("Usuario registrado correctamente");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Usuario registrado correctamente");
    }
}