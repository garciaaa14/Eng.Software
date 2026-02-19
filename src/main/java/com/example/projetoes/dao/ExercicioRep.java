package com.example.projetoes.dao;

import com.example.projetoes.domain.Exercicio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExercicioRep extends JpaRepository<Exercicio, Long> {

}
