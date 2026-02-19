package com.example.projetoes.service;

import com.example.projetoes.dao.*;
import com.example.projetoes.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class ExercicioServiceTest {

    @Autowired private ExercicioService exercicioService;

    @Autowired private UCRep ucRep;
    @Autowired private ExercicioRep exercicioRep;
    @Autowired private EtapaRep etapaRep;
    @Autowired private EstudanteExercicioRep eeRep;
    @Autowired private EstudanteExercicioEtapaRep eeeRep;

    private Long ucId;

    @BeforeEach
    void setUp() {
        UC uc = ucRep.findByDesignacao("Engenharia de Software").orElseThrow();
        ucId = uc.getId();
    }

    @Test
    void criarExercicio_criaEtapas_eExecucoesParaTodosOsAlunos() {
        Exercicio e = exercicioService.criarExercicio(
                ucId,
                "Ex 1",
                "Enunciado",
                List.of("Etapa A", "Etapa B", "Etapa C")
        );

        assertThat(e.getId()).isNotNull();
        assertThat(etapaRep.countByExercicioId(e.getId())).isEqualTo(3);

        List<EstudanteExercicio> execucoes = eeRep.findByExercicioId(e.getId());
        assertThat(execucoes).hasSize(2);

        for (EstudanteExercicio ee : execucoes) {
            for (Etapa etapa : e.getEtapas()) {
                assertThat(eeeRep.findByEeIdAndEtapaId(ee.getId(), etapa.getId())).isPresent();
            }
        }

        assertThat(exercicioRep.findById(e.getId())).isPresent();
    }

    @Test
    void marcarEtapaConcluida_marcaStatusComoConcluido_eDataConclusao() {
        Exercicio e = exercicioService.criarExercicio(ucId, "Ex 2", "Enun", List.of("E1", "E2"));

        EstudanteExercicio ee = eeRep.findByExercicioId(e.getId()).get(0);
        Etapa etapa = e.getEtapas().get(0);

        exercicioService.marcarEtapaConcluida(ee.getId(), etapa.getId());

        EstudanteExercicioEtapa status =
                eeeRep.findByEeIdAndEtapaId(ee.getId(), etapa.getId()).orElseThrow();

        assertThat(status.isConcluida()).isTrue();
        assertThat(status.getDataConclusao()).isNotNull();
    }

    @Test
    void chamarDocente_marcaChamouDocente_eDataChamada() {
        Exercicio e = exercicioService.criarExercicio(ucId, "Ex 3", "Enun", List.of("E1"));

        EstudanteExercicio ee = eeRep.findByExercicioId(e.getId()).get(0);

        exercicioService.chamarDocente(ee.getId());

        EstudanteExercicio atualizado = eeRep.findById(ee.getId()).orElseThrow();
        assertThat(atualizado.isChamouDocente()).isTrue();
        assertThat(atualizado.getDataChamada()).isNotNull();
    }

    @Test
    void atribuirNota_terminaExecucao_eGuardaNota() {
        Exercicio e = exercicioService.criarExercicio(ucId, "Ex 4", "Enun", List.of("E1"));

        EstudanteExercicio ee = eeRep.findByExercicioId(e.getId()).get(0);

        exercicioService.atribuirNota(ee.getId(), 15.5);

        EstudanteExercicio atualizado = eeRep.findById(ee.getId()).orElseThrow();
        assertThat(atualizado.isTerminado()).isTrue();
        assertThat(atualizado.getNota()).isEqualTo(15.5);
        assertThat(atualizado.getDataTermino()).isNotNull();
    }
}
