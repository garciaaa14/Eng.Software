package com.example.projetoes.dao;

import com.example.projetoes.domain.Docente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DocenteRep extends JpaRepository<Docente, Long> {

    @Query("SELECT d FROM Docente d WHERE d.email = ?1")
    Optional<Docente> findByEmail(String email);
}
