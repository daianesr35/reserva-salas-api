package com.seuprojeto.reserva_salas_api.service;

import com.seuprojeto.reserva_salas_api.model.Role;
import com.seuprojeto.reserva_salas_api.model.Usuario;
import com.seuprojeto.reserva_salas_api.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario registrarUsuario(String nome, String email, String senha, Role role) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email já cadastrado");
        }
        String hash = passwordEncoder.encode(senha);
        Usuario u = new Usuario(nome, email, hash, role == null ? Role.ROLE_USER : role);
        return usuarioRepository.save(u);
    }

    public Usuario getByEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
    }

    public boolean senhaValida(String senhaPura, String senhaHash) {
        return passwordEncoder.matches(senhaPura, senhaHash);
    }
}
