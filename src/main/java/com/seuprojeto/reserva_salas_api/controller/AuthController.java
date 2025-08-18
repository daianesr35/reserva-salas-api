package com.seuprojeto.reserva_salas_api.controller;

import com.seuprojeto.reserva_salas_api.dto.LoginRequest;
import com.seuprojeto.reserva_salas_api.dto.RegisterRequest;
import com.seuprojeto.reserva_salas_api.dto.TokenResponse;
import com.seuprojeto.reserva_salas_api.model.Role;
import com.seuprojeto.reserva_salas_api.model.Usuario;
import com.seuprojeto.reserva_salas_api.security.JwtUtil;
import com.seuprojeto.reserva_salas_api.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;

    public AuthController(UsuarioService usuarioService, JwtUtil jwtUtil) {
        this.usuarioService = usuarioService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Validated @RequestBody RegisterRequest req) {
        Usuario u = usuarioService.registrarUsuario(req.nome, req.email, req.senha, Role.ROLE_USER);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Validated @RequestBody LoginRequest req) {
        Usuario u = usuarioService.getByEmail(req.email);
        if (!usuarioService.senhaValida(req.senha, u.getSenhaHash())) {
            return ResponseEntity.status(401).build();
        }
        String token = jwtUtil.generateToken(u.getEmail(), u.getRole().name());
        return ResponseEntity.ok(new TokenResponse(token));
    }
}
