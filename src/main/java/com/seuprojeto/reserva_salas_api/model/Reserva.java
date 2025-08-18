package com.seuprojeto.reserva_salas_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(
        name = "reservas",
        indexes = { @Index(name = "ix_reserva_sala_data", columnList = "sala_id,data") }
)

public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // cada reserva pertence a 1 sala e 1 usuário
    @ManyToOne(optional = false)
    @JoinColumn(name = "sala_id")
    private Sala sala;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @NotNull
    private LocalDate data;

    @NotNull
    @Column(name = "hora_inicio")
    private LocalTime horaInicio;

    @NotNull
    @Column(name = "hora_fim")
    private LocalTime horaFim;

    public Reserva() {}

    public Reserva(Sala sala, Usuario usuario, LocalDate data, LocalTime horaInicio, LocalTime horaFim) {
        this.sala = sala;
        this.usuario = usuario;
        this.data = data;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
    }

    // getters e setters
    public Long getId() { return id; }
    public Sala getSala() { return sala; }
    public void setSala(Sala sala) { this.sala = sala; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }
    public LocalTime getHoraFim() { return horaFim; }
    public void setHoraFim(LocalTime horaFim) { this.horaFim = horaFim; }
}
