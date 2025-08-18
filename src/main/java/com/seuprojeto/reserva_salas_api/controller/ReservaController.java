package com.seuprojeto.reserva_salas_api.controller;

import com.seuprojeto.reserva_salas_api.dto.ReservaCreateRequest;
import com.seuprojeto.reserva_salas_api.dto.ReservaResponse;
import com.seuprojeto.reserva_salas_api.dto.ReservaUpdateRequest;
import com.seuprojeto.reserva_salas_api.model.Reserva;
import com.seuprojeto.reserva_salas_api.model.Usuario;
import com.seuprojeto.reserva_salas_api.service.ReservaService;
import com.seuprojeto.reserva_salas_api.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaController {
    private final ReservaService reservaService;
    private final UsuarioService usuarioService;

    public ReservaController(ReservaService reservaService, UsuarioService usuarioService) {
        this.reservaService = reservaService;
        this.usuarioService = usuarioService;
    }

    // Lista reservas do usuário autenticado
    @GetMapping
    public ResponseEntity<List<ReservaResponse>> minhasReservas(Principal principal) {
        Usuario u = usuarioService.getByEmail(principal.getName());
        var resp = reservaService.listarPorUsuario(u.getId()).stream().map(this::toResp).toList();
        return ResponseEntity.ok(resp);
    }

    // Cria reserva para o usuário autenticado
    @PostMapping
    public ResponseEntity<ReservaResponse> criar(@Validated @RequestBody ReservaCreateRequest req,
                                                 Principal principal) {
        Usuario u = usuarioService.getByEmail(principal.getName());
        Reserva r = reservaService.criar(
                req.salaId, u.getId(),
                LocalDate.parse(req.data),
                LocalTime.parse(req.horaInicio),
                LocalTime.parse(req.horaFim)
        );
        return ResponseEntity.ok(toResp(r));
    }

    // Atualiza uma reserva do usuário autenticado
    @PutMapping("/{id}")
    public ResponseEntity<ReservaResponse> atualizar(@PathVariable Long id,
                                                     @Validated @RequestBody ReservaUpdateRequest req,
                                                     Principal principal) {
        Usuario u = usuarioService.getByEmail(principal.getName());
        Reserva r = reservaService.atualizar(
                id, u.getId(),
                LocalDate.parse(req.data),
                LocalTime.parse(req.horaInicio),
                LocalTime.parse(req.horaFim)
        );
        return ResponseEntity.ok(toResp(r));
    }

    // Cancela uma reserva do usuário autenticado
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable Long id, Principal principal) {
        Usuario u = usuarioService.getByEmail(principal.getName());
        reservaService.cancelar(id, u.getId());
        return ResponseEntity.noContent().build();
    }

    // --------- helper ---------
    private ReservaResponse toResp(Reserva r) {
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
