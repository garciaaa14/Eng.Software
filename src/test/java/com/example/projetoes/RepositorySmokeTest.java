package com.example.projetoes;

import com.example.projetoes.domain.Docente;
import com.example.projetoes.dao.DocenteRep;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RepositorySmokeTest {

    @Autowired
    private DocenteRep docenteRep;

    @Test
    void guardaELeDocente() {
        Docente d = new Docente("Prof Teste", "profteste@escola.pt", "hash", "DEI");
        Docente saved = docenteRep.save(d);

        assertThat(saved.getId()).isNotNull();
        assertThat(docenteRep.findById(saved.getId())).isPresent();
    }
}
