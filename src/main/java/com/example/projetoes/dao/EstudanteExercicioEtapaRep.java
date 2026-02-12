package com.example.projetoes.dao;

import com.example.projetoes.domain.EstudanteExercicioEtapa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EstudanteExercicioEtapaRep extends JpaRepository<EstudanteExercicioEtapa, Long> {

    // Todas as etapas de um estudante num exercício
    @Query("SELECT e FROM EstudanteExercicioEtapa e " +
            "WHERE e.estudanteExercicio.estudante.id = ?1 " +
            "AND e.estudanteExercicio.exercicio.id = ?2")
    List<EstudanteExercicioEtapa> findByEstudanteAndExercicio(Long estudanteId, Long exercicioId);

    // Um registo específico (estudante + exercicio + etapa)
    @Query("SELECT e FROM EstudanteExercicioEtapa e " +
            "WHERE e.estudanteExercicio.estudante.id = ?1 " +
            "AND e.estudanteExercicio.exercicio.id = ?2 " +
            "AND e.etapa.id = ?3")
    Optional<EstudanteExercicioEtapa> findByEstudanteExercicioEtapa(Long estudanteId, Long exercicioId, Long etapaId);

    // Etapas concluídas (útil para calcular progresso)
    @Query("SELECT e FROM EstudanteExercicioEtapa e " +
            "WHERE e.estudanteExercicio.estudante.id = ?1 " +
            "AND e.estudanteExercicio.exercicio.id = ?2 " +
            "AND e.concluida = true")
    List<EstudanteExercicioEtapa> findConcluidasByEstudanteAndExercicio(Long estudanteId, Long exercicioId);
}
