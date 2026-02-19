package com.example.projetoes.service;

import com.example.projetoes.dao.*;
import com.example.projetoes.domain.*;
import com.example.projetoes.service.dto.ProgressoAlunoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExercicioService {

    @Autowired private ExercicioRep exercicioRep;
    @Autowired private UCRep ucRep;
    @Autowired private EstudanteExercicioRep eeRep;
    @Autowired private EstudanteExercicioEtapaRep eeeRep;
    @Autowired private EtapaRep etapaRep;

    @Transactional
    public Exercicio criarExercicio(Long ucId, String titulo, String enunciado, List<String> descricoesEtapas) {
        UC uc = ucRep.findByIdWithAlunos(ucId).orElseThrow();

        Exercicio exercicio = new Exercicio(titulo, enunciado, uc);

        for (int i = 0; i < descricoesEtapas.size(); i++) {
            Etapa etapa = new Etapa(descricoesEtapas.get(i), i + 1, exercicio);
            exercicio.getEtapas().add(etapa);
        }

        Exercicio salvo = exercicioRep.save(exercicio);

        for (Estudante estudante : uc.getAlunos()) {
            EstudanteExercicio ee = new EstudanteExercicio(estudante, salvo);
            eeRep.save(ee);

            for (Etapa etapa : salvo.getEtapas()) {
                EstudanteExercicioEtapa eee = new EstudanteExercicioEtapa(ee, etapa);
                eeeRep.save(eee);
            }
        }
        return salvo;
    }

    @Transactional(readOnly = true)
    public List<EstudanteExercicio> getExecucoesDoExercicio(Long exercicioId) {
        return eeRep.findByExercicioId(exercicioId);
    }

    @Transactional
    public void marcarEtapaConcluida(Long eeId, Long etapaId, String email) {
        EstudanteExercicio ee = eeRep.findById(eeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Execução não encontrada: " + eeId));

        if (!ee.getEstudante().getEmail().equalsIgnoreCase(email)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Esta execução não é do estudante autenticado.");
        }

        EstudanteExercicioEtapa status = eeeRep.findByEeIdAndEtapaId(eeId, etapaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Etapa não encontrada nessa execução."));

        status.setConcluida(true);
        status.setDataConclusao(LocalDateTime.now());
        eeeRep.save(status);
    }


    @Transactional
    public void chamarDocente(Long eeId) {
        EstudanteExercicio ee = eeRep.findById(eeId).orElseThrow();
        ee.setChamouDocente(true);
        ee.setDataChamada(LocalDateTime.now());
        eeRep.save(ee);
    }

    @Transactional
    public void atribuirNota(Long eeId, Double nota) {
        EstudanteExercicio ee = eeRep.findById(eeId).orElseThrow();
        ee.setNota(nota);
        ee.setTerminado(true);
        ee.setDataTermino(LocalDateTime.now());
        eeRep.save(ee);
    }

    @Transactional(readOnly = true)
    public List<ProgressoAlunoDTO> dashboardProgresso(Long exercicioId) {
        long totalEtapas = etapaRep.countByExercicioId(exercicioId);

        return eeRep.findByExercicioId(exercicioId).stream().map(ee -> {
            long concluidas = eeeRep.countConcluidasByEeId(ee.getId());
            int etapaAtual = (int) concluidas; // 0 = nenhuma
            return new ProgressoAlunoDTO(
                    ee.getId(),
                    ee.getEstudante().getId(),
                    ee.getEstudante().getNome(),
                    ee.getEstudante().getNumero(),
                    totalEtapas,
                    concluidas,
                    etapaAtual,
                    ee.isChamouDocente(),
                    ee.isTerminado(),
                    ee.getNota()
            );
        }).toList();
    }

    @Transactional(readOnly = true)
    public List<EstudanteExercicioEtapa> listarEtapasDaExecucao(Long eeId, String email) {
        EstudanteExercicio ee = eeRep.findById(eeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Execução não encontrada: " + eeId));

        if (!ee.getEstudante().getEmail().equalsIgnoreCase(email)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Esta execução não é do estudante autenticado.");
        }

        return eeeRep.findByEeIdOrderByEtapaOrdem(eeId);
    }

}
