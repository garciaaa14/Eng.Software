package com.example.projetoes.dao;

import com.example.projetoes.domain.EstudanteExercicioEtapa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EstudanteExercicioEtapaRep extends JpaRepository<EstudanteExercicioEtapa, Long> {

    @Query("SELECT e FROM EstudanteExercicioEtapa e WHERE e.estudanteExercicio.id = ?1 AND e.etapa.id = ?2")
    Optional<EstudanteExercicioEtapa> findByEeIdAndEtapaId(Long estudanteExercicioId, Long etapaId);

    @Query("SELECT COUNT(e) FROM EstudanteExercicioEtapa e WHERE e.estudanteExercicio.id = ?1 AND e.concluida = true")
    long countConcluidasByEeId(Long estudanteExercicioId);

    @Query("SELECT e FROM EstudanteExercicioEtapa e " +
            "WHERE e.estudanteExercicio.id = ?1 " +
            "ORDER BY e.etapa.ordem ASC")
    List<EstudanteExercicioEtapa> findByEeIdOrderByEtapaOrdem(Long eeId);

}
