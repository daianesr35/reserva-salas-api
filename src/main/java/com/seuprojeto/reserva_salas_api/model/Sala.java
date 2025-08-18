package com.seuprojeto.reserva_salas_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "salas", uniqueConstraints = {
        @UniqueConstraint(name = "uk_sala_nome", columnNames = "nome")
})

public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @NotNull
    private Integer capacidade;

    @NotBlank
    private String local;

    public Sala() {}

    public Sala(String nome, Integer capacidade, String local) {
        this.nome = nome;
        this.capacidade = capacidade;
        this.local = local;
    }

    // getters e setters
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Integer getCapacidade() { return capacidade; }
    public void setCapacidade(Integer capacidade) { this.capacidade = capacidade; }
    public String getLocal() { return local; }
    public void setLocal(String local) { this.local = local; }
}
