package com.example.projetoes;

import com.example.projetoes.domain.Docente;
import com.example.projetoes.domain.Estudante;
import com.example.projetoes.domain.UC;
import com.example.projetoes.dao.DocenteRep;
import com.example.projetoes.dao.EstudanteRep;
import com.example.projetoes.dao.UCRep;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TestDataRunner implements CommandLineRunner {

    private final DocenteRep docenteRep;
    private final EstudanteRep estudanteRep;
    private final UCRep ucRep;

    public TestDataRunner(DocenteRep docenteRep, EstudanteRep estudanteRep, UCRep ucRep) {
        this.docenteRep = docenteRep;
        this.estudanteRep = estudanteRep;
        this.ucRep = ucRep;
    }

    @Override
    public void run(String... args) {
        if (docenteRep.count() > 0) {
            System.out.println("ℹ️ Já existe dados. A saltar inserção.");
            return;
        }

        // 1) Docente
        Docente d = new Docente("Prof A", "profA@escola.pt", "hash", "Informatica");
        docenteRep.save(d);

        // 2) UC
        UC uc = new UC("Engenharia de Software", "2025/2026", d);
        ucRep.save(uc);

        // 3) Estudante
        Estudante e1 = new Estudante("Aluno 1", "aluno1@escola.pt", "hash", "A123");
        e1.getUcs().add(uc);
        estudanteRep.save(e1);

        Estudante e2 = new Estudante("Aluno 2", "aluno2@escola.pt", "hash", "A124");
        e2.getUcs().add(uc);
        estudanteRep.save(e2);

        System.out.println("✅ Inseridos: Docente, UC e 2 Estudantes. UC id=" + uc.getId());
    }
}
