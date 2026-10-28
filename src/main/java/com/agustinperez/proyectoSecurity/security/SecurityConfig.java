package com.agustinperez.proyectoSecurity.security;

import com.agustinperez.proyectoSecurity.security.jwt.AuthTokenFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
public class SecurityConfig {

    private static final Logger log =
            LoggerFactory.getLogger(SecurityConfig.class);

    private final AuthTokenFilter authTokenFilter;

    public SecurityConfig(AuthTokenFilter authTokenFilter) {
        this.authTokenFilter = authTokenFilter;
        log.info("SecurityConfig initialized");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        log.debug("Initializing BCrypt password encoder");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        log.debug("Configuring AuthenticationManager");
        return config.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        log.debug("Configuring CORS policy");

        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(
                List.of("http://localhost:5173")
        );

        config.setAllowedMethods(
                List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
        );

        config.setAllowedHeaders(
                List.of("Authorization", "Content-Type", "Accept", "Origin")
        );

        config.setAllowCredentials(false);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", config);

        log.info("CORS configuration registered successfully");

        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        log.info("Configuring HTTP security filter chain");

        http
                .cors(cors -> {})
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(
                                "/auth/login",
                                "/auth/register"
                        ).permitAll()
                        .requestMatchers("/prueba", "/prueba/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(
                        authTokenFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        SecurityFilterChain filterChain = http.build();

        log.info("HTTP security filter chain configured successfully");

        return filterChain;
    }
}