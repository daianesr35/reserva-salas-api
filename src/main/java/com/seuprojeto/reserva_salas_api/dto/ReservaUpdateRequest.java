package com.seuprojeto.reserva_salas_api.dto;

import jakarta.validation.constraints.NotNull;

public class ReservaUpdateRequest {
    @NotNull
    public String data;

    @NotNull
    public String horaInicio;

    @NotNull
    public String horaFim;
}
