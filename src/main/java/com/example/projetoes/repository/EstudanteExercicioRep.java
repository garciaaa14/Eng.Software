package com.example.projetoes.repository;

import com.example.projetoes.domain.EstudanteExercicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstudanteExercicioRep extends JpaRepository<EstudanteExercicio, Long> {

    Optional<EstudanteExercicio> findByEstudanteIdAndExercicioId(Long estudanteId, Long exercicioId);
}