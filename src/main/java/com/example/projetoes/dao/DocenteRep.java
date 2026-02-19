package com.example.projetoes.dao;

import com.example.projetoes.domain.Docente;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DocenteRep extends JpaRepository<Docente, Long> {
}

