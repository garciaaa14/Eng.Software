package com.example.projetoes.dao;

import com.example.projetoes.domain.Estudante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EstudanteRep extends JpaRepository<Estudante, Long> {

    @Query("SELECT e FROM Estudante e WHERE e.numero = ?1")
    Optional<Estudante> findByNumero(String numero);

    @Query("SELECT e FROM Estudante e WHERE e.email = ?1")
    Optional<Estudante> findByEmail(String email);
}
