package com.example.projetoes.repository;

import com.example.projetoes.domain.Docente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DocenteRep extends JpaRepository<Docente, Long> {

    Optional<Docente> findByEmail(String email);
}
