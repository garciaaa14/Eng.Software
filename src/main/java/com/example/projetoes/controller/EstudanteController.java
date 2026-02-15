package com.example.projetoes.controller;

import com.example.projetoes.controller.dto.EstudanteDTO;
import com.example.projetoes.controller.dto.EstudanteExercicioDTO;
import com.example.projetoes.dao.EstudanteExercicioRep;
import com.example.projetoes.dao.EstudanteRep;
import com.example.projetoes.domain.Estudante;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estudantes")
public class EstudanteController {

    private final EstudanteRep estudanteRep;
    private final EstudanteExercicioRep estudanteExercicioRep;

    public EstudanteController(EstudanteRep estudanteRep, EstudanteExercicioRep estudanteExercicioRep) {
        this.estudanteRep = estudanteRep;
        this.estudanteExercicioRep = estudanteExercicioRep;
    }

    @GetMapping("/{id}")
    public EstudanteDTO get(@PathVariable Long id) {
        Estudante e = estudanteRep.findById(id).orElseThrow();
        return new EstudanteDTO(
                e.getId(),
                e.getNome(),
                e.getEmail(),
                e.getNumero()
        );
    }

    @GetMapping("/{id}/exercicios")
    public List<EstudanteExercicioDTO> exerciciosDoEstudante(@PathVariable Long id) {
        return estudanteExercicioRep.findByEstudanteId(id).stream()
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
}
