package com.seuprojeto.reserva_salas_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity

@Table(name = "usuarios", uniqueConstraints = {
        @UniqueConstraint(name = "uk_usuario_email", columnNames = "email")
})

public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @Email
    @NotBlank
    private String email;

    // guardaremos a senha já com hash (ex.: BCrypt) quando implementarmos a segurança
    @NotBlank
    @Column(name = "senha_hash")
    private String senhaHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.ROLE_USER;  // admin ou usuário comum

    public Usuario() {}

    public Usuario(String nome, String email, String senhaHash, Role role) {
        this.nome = nome;
        this.email = email;
        this.senhaHash = senhaHash;
        this.role = role;
    }

    // getters e setters
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenhaHash() { return senhaHash; }
    public void setSenhaHash(String senhaHash) { this.senhaHash = senhaHash; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}
