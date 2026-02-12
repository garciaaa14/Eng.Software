package com.example.projetoes.dao;

import com.example.projetoes.domain.EstudanteExercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EstudanteExercicioRep extends JpaRepository<EstudanteExercicio, Long> {

    @Query("SELECT e FROM EstudanteExercicio e WHERE e.estudante.id = ?1 AND e.exercicio.id = ?2")
    Optional<EstudanteExercicio> findByEstudanteIdAndExercicioId(Long estudanteId, Long exercicioId);

    // Para o docente: ver todos os estudantes num exercício (tabela/progresso)
    @Query("SELECT e FROM EstudanteExercicio e WHERE e.exercicio.id = ?1")
    List<EstudanteExercicio> findByExercicioId(Long exercicioId);

    // Ver quem chamou o docente num exercício
    @Query("SELECT e FROM EstudanteExercicio e WHERE e.exercicio.id = ?1 AND e.chamouDocente = true")
    List<EstudanteExercicio> findChamadasByExercicioId(Long exercicioId);

    // Exercícios de um estudante (para “o exercício aparecer ao estudante”)
    @Query("SELECT e FROM EstudanteExercicio e WHERE e.estudante.id = ?1")
    List<EstudanteExercicio> findByEstudanteId(Long estudanteId);
}
