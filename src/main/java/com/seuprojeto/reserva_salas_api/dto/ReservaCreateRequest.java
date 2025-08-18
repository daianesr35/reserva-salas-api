package com.seuprojeto.reserva_salas_api.dto;

import jakarta.validation.constraints.NotNull;

public class ReservaCreateRequest {
    @NotNull
    public Long salaId;

    @NotNull
    public String data;       // formato "2025-01-01"

    @NotNull
    public String horaInicio; // formato "08:00"

    @NotNull
    public String horaFim;    // formato "10:00"
}
