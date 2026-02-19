package com.example.projetoes.dao;

import com.example.projetoes.domain.EstudanteExercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EstudanteExercicioRep extends JpaRepository<EstudanteExercicio, Long> {


    //Docente: ver todos os estudantes num exercício
    @Query("SELECT e FROM EstudanteExercicio e WHERE e.exercicio.id = ?1")
    List<EstudanteExercicio> findByExercicioId(Long exercicioId);

    // Exercícios de um estudante
    @Query("SELECT e FROM EstudanteExercicio e WHERE e.estudante.id = ?1")
    List<EstudanteExercicio> findByEstudanteId(Long estudanteId);
}
