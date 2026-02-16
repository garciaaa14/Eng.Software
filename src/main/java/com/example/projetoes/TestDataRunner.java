package com.example.projetoes;

import com.example.projetoes.dao.*;
import com.example.projetoes.domain.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class TestDataRunner implements CommandLineRunner {

    private final DocenteRep docenteRep;
    private final EstudanteRep estudanteRep;
    private final UCRep ucRep;
    private final RoleRep roleRep;
    private final PasswordEncoder passwordEncoder;

    public TestDataRunner(
            DocenteRep docenteRep,
            EstudanteRep estudanteRep,
            UCRep ucRep,
            RoleRep roleRep,
            PasswordEncoder passwordEncoder
    ) {
        this.docenteRep = docenteRep;
        this.estudanteRep = estudanteRep;
        this.ucRep = ucRep;
        this.roleRep = roleRep;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        if (docenteRep.count() > 0) {
            System.out.println("ℹ️ Já existem dados. A saltar inserção.");
            return;
        }

        Role roleDocente = roleRep.findByName("ROLE_DOCENTE")
                .orElseGet(() -> roleRep.save(new Role("ROLE_DOCENTE")));

        Role roleEstudante = roleRep.findByName("ROLE_ESTUDANTE")
                .orElseGet(() -> roleRep.save(new Role("ROLE_ESTUDANTE")));

        String passProf = passwordEncoder.encode("123");
        String passAluno = passwordEncoder.encode("123");

        Docente d = new Docente("Prof A", "profA@escola.pt", passProf, "Informatica");
        d.getRoles().add(roleDocente);
        docenteRep.save(d);

        UC uc = new UC("Engenharia de Software", "2025/2026", d);
        ucRep.save(uc);

        Estudante e1 = new Estudante("Aluno 1", "aluno1@escola.pt", passAluno, "A123");
        e1.getRoles().add(roleEstudante);
        e1.getUcs().add(uc);
        estudanteRep.save(e1);

        Estudante e2 = new Estudante("Aluno 2", "aluno2@escola.pt", passAluno, "A124");
        e2.getRoles().add(roleEstudante);
        e2.getUcs().add(uc);
        estudanteRep.save(e2);

        System.out.println("Inseridos: roles, docente, UC e 2 estudantes.");
    }
}
