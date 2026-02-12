package com.example.projetoes.repository;

import com.example.projetoes.domain.Exercicio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExercicioRep extends JpaRepository<Exercicio, Long> {
}