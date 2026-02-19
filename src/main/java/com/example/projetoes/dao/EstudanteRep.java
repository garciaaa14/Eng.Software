package com.example.projetoes.dao;

import com.example.projetoes.domain.Estudante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstudanteRep extends JpaRepository<Estudante, Long> {

}
