package com.seuprojeto.reserva_salas_api.repository;

import com.seuprojeto.reserva_salas_api.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaRepository extends JpaRepository<Sala, Long> {
    boolean existsByNome(String nome);
}
