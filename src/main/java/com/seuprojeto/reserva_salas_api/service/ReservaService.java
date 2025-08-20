package com.seuprojeto.reserva_salas_api.service;

import com.seuprojeto.reserva_salas_api.model.Reserva;
import com.seuprojeto.reserva_salas_api.model.Sala;
import com.seuprojeto.reserva_salas_api.model.Usuario;
import com.seuprojeto.reserva_salas_api.repository.ReservaRepository;
import com.seuprojeto.reserva_salas_api.repository.SalaRepository;
import com.seuprojeto.reserva_salas_api.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final SalaRepository salaRepository;
    private final UsuarioRepository usuarioRepository;

    public ReservaService(ReservaRepository reservaRepository,
                          SalaRepository salaRepository,
                          UsuarioRepository usuarioRepository) {
        this.reservaRepository = reservaRepository;
        this.salaRepository = salaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Reserva criar(Long salaId, Long usuarioId,
                         LocalDate data, LocalTime inicio, LocalTime fim) {

        validarIntervalo(inicio, fim);

        Sala sala = salaRepository.findById(salaId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Sala não encontrada"));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        boolean conflito = reservaRepository.existsConflito(salaId, data, inicio, fim);
        if (conflito) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Conflito de horário para a sala");
        }

        Reserva r = new Reserva(sala, usuario, data, inicio, fim);
        return reservaRepository.save(r);
    }

    public Reserva atualizar(Long reservaId, Long usuarioId,
                             LocalDate data, LocalTime inicio, LocalTime fim) {

        validarIntervalo(inicio, fim);

        Reserva r = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Reserva não encontrada"));

        // regra: usuário só altera a própria reserva
        if (!r.getUsuario().getId().equals(usuarioId)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Não pode editar reserva de outro usuário");
        }

        boolean conflito = reservaRepository.existsConflito(r.getSala().getId(), data, inicio, fim);

        // Se realmente mudou data/horário e há conflito, bloqueia
        boolean mudou = !data.equals(r.getData())
                || !inicio.equals(r.getHoraInicio())
                || !fim.equals(r.getHoraFim());

        if (mudou && conflito) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Conflito de horário para a sala");
        }

        r.setData(data);
        r.setHoraInicio(inicio);
        r.setHoraFim(fim);
        return reservaRepository.save(r);
    }

    public void cancelar(Long reservaId, Long usuarioId) {
        Reserva r = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Reserva não encontrada"));

        if (!r.getUsuario().getId().equals(usuarioId)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Não pode cancelar reserva de outro usuário");
        }
        reservaRepository.delete(r);
    }

    public List<Reserva> listarPorUsuario(Long usuarioId) {
        return reservaRepository.findByUsuarioId(usuarioId);
    }

    public List<Reserva> listarDaSalaNoDia(Long salaId, LocalDate data) {
        return reservaRepository.findBySalaIdAndData(salaId, data);
    }

    private void validarIntervalo(LocalTime inicio, LocalTime fim) {
        if (!inicio.isBefore(fim)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Hora início deve ser antes da hora fim");
        }
        // (opcional) outras regras de negócio
    }
}
