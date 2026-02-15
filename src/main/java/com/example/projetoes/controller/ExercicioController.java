package com.example.projetoes.controller;

import com.example.projetoes.controller.dto.ExercicioCreateDTO;
import com.example.projetoes.controller.dto.ExercicioResponseDTO;
import com.example.projetoes.controller.dto.EstudanteExercicioDTO;
import com.example.projetoes.domain.EstudanteExercicio;
import com.example.projetoes.domain.Exercicio;
import com.example.projetoes.service.ExercicioService;
import com.example.projetoes.service.dto.ProgressoAlunoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercicios")
public class ExercicioController {

    private final ExercicioService exercicioService;

    public ExercicioController(ExercicioService exercicioService) {
        this.exercicioService = exercicioService;
    }

    @PostMapping("/uc/{ucId}")
    public ExercicioResponseDTO criar(@PathVariable Long ucId, @RequestBody ExercicioCreateDTO dto) {

        if (dto.etapas == null || dto.etapas.isEmpty()) {
            throw new IllegalArgumentException("É obrigatório enviar a lista 'etapas'");
        }

        Exercicio e = exercicioService.criarExercicio(ucId, dto.titulo, dto.enunciado, dto.etapas);
        return new ExercicioResponseDTO(e.getId(), e.getTitulo());
    }

    @GetMapping("/{id}/progresso")
    public List<EstudanteExercicioDTO> verProgresso(@PathVariable Long id) {
        return exercicioService.getExecucoesDoExercicio(id).stream()
                .map(ee -> new EstudanteExercicioDTO(
                        ee.getId(),
                        ee.getEstudante().getId(),
                        ee.getEstudante().getNome(),
                        ee.getEstudante().getNumero(),
                        ee.getExercicio().getId(),
                        ee.getExercicio().getTitulo(),
                        ee.isChamouDocente(),
                        ee.isTerminado(),
                        ee.getNota()
                ))
                .toList();
    }


    @GetMapping("/{id}/dashboard")
    public List<ProgressoAlunoDTO> dashboard(@PathVariable Long id) {
        return exercicioService.dashboardProgresso(id);
    }

    @PatchMapping("/execucao/{eeId}/etapa/{etapaId}/concluir")
    public void concluirEtapa(@PathVariable Long eeId, @PathVariable Long etapaId) {
        exercicioService.marcarEtapaConcluida(eeId, etapaId);
    }

    @PutMapping("/execucao/{eeId}/chamar")
    public void chamarDocente(@PathVariable Long eeId) {
        exercicioService.chamarDocente(eeId);
    }

    @PutMapping("/execucao/{eeId}/nota")
    public void darNota(@PathVariable Long eeId, @RequestParam Double nota) {
        exercicioService.atribuirNota(eeId, nota);
    }
}
