package com.seuprojeto.reserva_salas_api.service;

import com.seuprojeto.reserva_salas_api.model.Sala;
import com.seuprojeto.reserva_salas_api.repository.SalaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class SalaService {
    private final SalaRepository salaRepository;

    public SalaService(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    public Sala criar(Sala sala) {
        if (salaRepository.existsByNome(sala.getNome())) {
            throw new IllegalArgumentException("Sala já existe");
        }
        return salaRepository.save(sala);
    }

    public List<Sala> listar() {
        return salaRepository.findAll();
    }

    public Sala atualizar(Long id, Sala dados) {
        Sala atual = salaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sala não encontrada"));
        atual.setNome(dados.getNome());
        atual.setCapacidade(dados.getCapacidade());
        atual.setLocal(dados.getLocal());
        return salaRepository.save(atual);
    }

    public void deletar(Long id) {
        salaRepository.deleteById(id);
    }
}
