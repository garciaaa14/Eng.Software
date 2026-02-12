package com.example.projetoes.dao;

import com.example.projetoes.domain.Exercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExercicioRep extends JpaRepository<Exercicio, Long> {

    @Query("SELECT e FROM Exercicio e WHERE e.uc.id = ?1")
    List<Exercicio> findByUnidadeCurricularId(Long unidadeCurricularId);


}
