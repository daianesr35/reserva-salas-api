package com.seuprojeto.reserva_salas_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class RegisterRequest {
    @NotBlank
    public String nome;

    @Email
    @NotBlank
    public String email;

    @NotBlank
    public String senha;

}
