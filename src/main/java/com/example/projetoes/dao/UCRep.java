package com.example.projetoes.dao;

import com.example.projetoes.domain.UC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UCRep extends JpaRepository<UC, Long> {

    // Buscar UC por designação
    @Query("SELECT u FROM UC u WHERE u.designacao = ?1")
    Optional<UC> findByDesignacao(String designacao);

    // Listar UCs de um docente
    @Query("SELECT u FROM UC u WHERE u.docente.id = ?1")
    List<UC> findByDocenteId(Long docenteId);

    // Listar UCs onde um estudante está inscrito
    @Query("SELECT u FROM UC u JOIN u.alunos a WHERE a.id = ?1")
    List<UC> findByEstudanteId(Long estudanteId);

    @Query("SELECT u FROM UC u JOIN FETCH u.alunos WHERE u.id = ?1")
    Optional<UC> findByIdWithAlunos(Long id);



}
