package com.seuprojeto.reserva_salas_api.repository;

import com.seuprojeto.reserva_salas_api.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    // Conflito se (novo.inicio < existente.fim) e (novo.fim > existente.inicio)
    @Query("""
           SELECT COUNT(r) > 0
           FROM Reserva r
           WHERE r.sala.id = :salaId
             AND r.data = :data
             AND (:inicio < r.horaFim)
             AND (:fim > r.horaInicio)
           """)
    boolean existsConflito(@Param("salaId") Long salaId,
                           @Param("data") LocalDate data,
                           @Param("inicio") LocalTime inicio,
                           @Param("fim") LocalTime fim);

    List<Reserva> findByUsuarioId(Long usuarioId);
    List<Reserva> findBySalaIdAndData(Long salaId, LocalDate data);
}
