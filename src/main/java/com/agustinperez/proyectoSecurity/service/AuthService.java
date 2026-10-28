package com.agustinperez.proyectoSecurity.service;

import com.agustinperez.proyectoSecurity.dto.JwtResponse;
import com.agustinperez.proyectoSecurity.dto.LoginRequest;
import com.agustinperez.proyectoSecurity.dto.RegisterRequest;
import com.agustinperez.proyectoSecurity.model.Role;
import com.agustinperez.proyectoSecurity.model.User;
import com.agustinperez.proyectoSecurity.repository.RoleRepository;
import com.agustinperez.proyectoSecurity.repository.UserRepository;

import com.agustinperez.proyectoSecurity.security.jwt.JwtUtils;
import com.agustinperez.proyectoSecurity.model.ERole;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class AuthService {

    private static final Logger log =
            LoggerFactory.getLogger(AuthService.class);

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public AuthService(
            AuthenticationManager authenticationManager,
            JwtUtils jwtUtils,
            PasswordEncoder passwordEncoder,
            UserRepository userRepository,
            RoleRepository roleRepository) {

        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;

        log.info("AuthService initialized");
    }

    public JwtResponse login(LoginRequest request) {

        log.debug("Authentication attempt started");

        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        String email = authentication.getName();

        log.info("User authenticated successfully | email={}", email);

        String jwt = jwtUtils.generateJwtToken(email);

        log.debug("JWT generated successfully for authenticated user");

        return new JwtResponse(jwt);
    }

    @Transactional
    public void register(RegisterRequest request) {

        log.debug("User registration process started");

        if (userRepository.existsByEmail(request.getEmail())) {
            log.warn("User registration rejected | reason=email already registered");

            throw new IllegalArgumentException(
                    "Ya existe una cuenta registrada con ese email"
            );
        }

        Role userRole = roleRepository.findByNameRole(ERole.ROLE_USER)
                .orElseThrow(() -> {
                    log.error("User registration failed | reason=ROLE_USER not found");

                    return new IllegalStateException(
                            "No se encontró el rol ROLE_USER"
                    );
                });

        User user = new User();
        user.setName(request.getName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of(userRole));

        userRepository.save(user);

        log.info("User registered successfully | email={}", user.getEmail());
    }
}