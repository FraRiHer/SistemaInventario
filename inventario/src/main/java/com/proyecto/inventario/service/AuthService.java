package com.proyecto.inventario.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.proyecto.inventario.Auth.AuthResponse;
import com.proyecto.inventario.Auth.LoginRequest;
import com.proyecto.inventario.Auth.RegisterRequest;
import com.proyecto.inventario.Jwt.JwtService;
import com.proyecto.inventario.entities.Rol;
import com.proyecto.inventario.entities.Usuario;
import com.proyecto.inventario.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    @Autowired
    private final UsuarioRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        Usuario user=userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token=jwtService.getToken(user);
        return AuthResponse.builder()
            .token(token)
            .build();

    }

    public AuthResponse register(RegisterRequest request) {
        Usuario user = Usuario.builder()
            .username(request.getUsername())
            .password(passwordEncoder.encode(request.getPassword()))
            .nombre(request.getNombre())
            .email(request.getEmail())
            .rol(Rol.valueOf(request.getRol().toUpperCase()))
            .build();
        // Guarda el usuario y captura la entidad guardada para obtener el ID generado
        Usuario savedUser = userRepository.save(user);
        String token = jwtService.getToken(savedUser);

        return AuthResponse.builder()
            .token(token)
            .build();
    }
}
