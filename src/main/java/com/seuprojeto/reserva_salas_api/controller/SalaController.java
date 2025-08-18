package com.seuprojeto.reserva_salas_api.controller;

import com.seuprojeto.reserva_salas_api.dto.ReservaResponse;
import com.seuprojeto.reserva_salas_api.dto.SalaRequest;
import com.seuprojeto.reserva_salas_api.dto.SalaResponse;
import com.seuprojeto.reserva_salas_api.model.Reserva;
import com.seuprojeto.reserva_salas_api.model.Sala;
import com.seuprojeto.reserva_salas_api.service.ReservaService;
import com.seuprojeto.reserva_salas_api.service.SalaService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/salas")
public class SalaController {
    private final SalaService salaService;
    private final ReservaService reservaService;

    public SalaController(SalaService salaService, ReservaService reservaService) {
        this.salaService = salaService;
        this.reservaService = reservaService;
    }

    @GetMapping
    public ResponseEntity<List<SalaResponse>> listar() {
        var resp = salaService.listar().stream().map(this::toResponse).toList();
        return ResponseEntity.ok(resp);
    }

    // Somente ADMIN pode criar salas
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<SalaResponse> criar(@Validated @RequestBody SalaRequest req) {
        Sala s = salaService.criar(new Sala(req.nome, req.capacidade, req.local));
        return ResponseEntity.ok(toResponse(s));
    }

    // Somente ADMIN pode atualizar salas
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<SalaResponse> atualizar(@PathVariable Long id,
                                                  @Validated @RequestBody SalaRequest req) {
        Sala s = salaService.atualizar(id, new Sala(req.nome, req.capacidade, req.local));
        return ResponseEntity.ok(toResponse(s));
    }

    // Somente ADMIN pode deletar salas
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        salaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // Disponibilidade: retorna as reservas existentes para uma sala em uma data
    @GetMapping("/disponibilidade")
    public ResponseEntity<List<ReservaResponse>> disponibilidade(@RequestParam Long salaId,
                                                                 @RequestParam String data) {
        LocalDate dia = LocalDate.parse(data);
        List<Reserva> reservas = reservaService.listarDaSalaNoDia(salaId, dia);
        var resp = reservas.stream().map(this::toReservaResp).toList();
        return ResponseEntity.ok(resp);
    }

    // --------- helpers ---------
    private SalaResponse toResponse(Sala s) {
        var r = new SalaResponse();
        r.id = s.getId();
        r.nome = s.getNome();
        r.capacidade = s.getCapacidade();
        r.local = s.getLocal();
        return r;
    }

    private ReservaResponse toReservaResp(Reserva r) {
        var rr = new ReservaResponse();
        rr.id = r.getId();
        rr.salaId = r.getSala().getId();
        rr.usuarioId = r.getUsuario().getId();
        rr.data = r.getData().toString();
        rr.horaInicio = r.getHoraInicio().toString();
        rr.horaFim = r.getHoraFim().toString();
        return rr;
    }
}
