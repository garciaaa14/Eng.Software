package com.example.projetoes.controller;

import com.example.projetoes.controller.dto.ExercicioCreateDTO;
import com.example.projetoes.controller.dto.ExercicioResponseDTO;
import com.example.projetoes.controller.dto.EstudanteExercicioDTO;
import com.example.projetoes.domain.Exercicio;
import com.example.projetoes.service.ExercicioService;
import com.example.projetoes.service.dto.ProgressoAlunoDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercicios")
public class ExercicioController {

    private final ExercicioService exercicioService;

    public ExercicioController(ExercicioService exercicioService) {
        this.exercicioService = exercicioService;
    }


    @PreAuthorize("hasRole('DOCENTE')")
    @PostMapping("/uc/{ucId}")
    public ExercicioResponseDTO criar(@PathVariable Long ucId, @RequestBody ExercicioCreateDTO dto) {

        if (dto == null || dto.titulo == null || dto.titulo.isBlank()) {
            throw new IllegalArgumentException("É obrigatório enviar 'titulo'");
        }
        if (dto.enunciado == null || dto.enunciado.isBlank()) {
            throw new IllegalArgumentException("É obrigatório enviar 'enunciado'");
        }
        if (dto.etapas == null || dto.etapas.isEmpty()) {
            throw new IllegalArgumentException("É obrigatório enviar a lista 'etapas'");
        }

        Exercicio e = exercicioService.criarExercicio(ucId, dto.titulo, dto.enunciado, dto.etapas);
        return new ExercicioResponseDTO(e.getId(), e.getTitulo());
    }

    @PreAuthorize("hasRole('DOCENTE')")
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

    @PreAuthorize("hasRole('DOCENTE')")
    @GetMapping("/{id}/dashboard")
    public List<ProgressoAlunoDTO> dashboard(@PathVariable Long id) {
        return exercicioService.dashboardProgresso(id);
    }

    @PreAuthorize("hasRole('ESTUDANTE')")
    @PatchMapping("/execucao/{eeId}/etapa/{etapaId}/concluir")
    public void concluirEtapa(@PathVariable Long eeId, @PathVariable Long etapaId) {
        exercicioService.marcarEtapaConcluida(eeId, etapaId);
    }

    @PreAuthorize("hasRole('ESTUDANTE')")
    @PutMapping("/execucao/{eeId}/chamar")
    public void chamarDocente(@PathVariable Long eeId) {
        exercicioService.chamarDocente(eeId);
    }

    @PreAuthorize("hasRole('DOCENTE')")
    @PutMapping("/execucao/{eeId}/nota")
    public void darNota(@PathVariable Long eeId, @RequestParam Double nota) {
        if (nota == null || nota < 0 || nota > 20) {
            throw new IllegalArgumentException("A nota tem de estar entre 0 e 20");
        }
        exercicioService.atribuirNota(eeId, nota);
    }
}
