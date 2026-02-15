package com.example.projetoes.dao;

import com.example.projetoes.domain.Etapa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EtapaRep extends JpaRepository<Etapa, Long> {

    @Query("SELECT e FROM Etapa e WHERE e.exercicio.id = ?1 ORDER BY e.ordem ASC")
    List<Etapa> findByExercicioIdOrderByOrdem(Long exercicioId);

    @Query("SELECT COUNT(e) FROM Etapa e WHERE e.exercicio.id = ?1")
    long countByExercicioId(Long exercicioId);

}
