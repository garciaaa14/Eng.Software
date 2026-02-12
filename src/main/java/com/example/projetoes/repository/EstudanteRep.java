package com.example.projetoes.repository;

import com.example.projetoes.domain.Estudante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstudanteRep extends JpaRepository<Estudante, Long> {

    Optional<Estudante> findByNumero(String numero);

    Optional<Estudante> findByEmail(String email);
}