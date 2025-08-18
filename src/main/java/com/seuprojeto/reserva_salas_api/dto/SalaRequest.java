package com.seuprojeto.reserva_salas_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SalaRequest {
    @NotBlank
    public String nome;

    @NotNull
    public Integer capacidade;

    @NotBlank
    public String local;
}
