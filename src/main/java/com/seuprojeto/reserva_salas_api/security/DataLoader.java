package com.seuprojeto.reserva_salas_api.security;

import com.seuprojeto.reserva_salas_api.model.Role;
import com.seuprojeto.reserva_salas_api.model.Usuario;
import com.seuprojeto.reserva_salas_api.repository.UsuarioRepository;
import com.seuprojeto.reserva_salas_api.service.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;

    public DataLoader(UsuarioRepository usuarioRepository, UsuarioService usuarioService) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
    }

    @Override
    public void run(String... args) {
        if (usuarioRepository.findByEmail("admin@ifsertao-pe.com").isEmpty()) {
            usuarioService.registrarUsuario("Admin", "admin@ifsertao-pe.com", "admin123", Role.ROLE_ADMIN);
            System.out.println("ADMIN criado: admin@ifsertao-pe.com / admin123");
        }
    }
}
