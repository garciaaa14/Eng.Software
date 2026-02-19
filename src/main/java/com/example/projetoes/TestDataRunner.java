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
            System.out.println("Já existem dados.");
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

        UC uc1 = new UC("Engenharia de Software", "2025/2026", d);
        UC uc2 = new UC("Sistemas Distribuidos", "2025/2026", d);

        try {
            d.getUcs().add(uc1);
            d.getUcs().add(uc2);
        } catch (Exception ignored) {
        }

        docenteRep.save(d);
        ucRep.save(uc1);
        ucRep.save(uc2);

        Estudante e1 = new Estudante("Aluno 1", "aluno1@escola.pt", passAluno, "A123");
        e1.getRoles().add(roleEstudante);
        e1.getUcs().add(uc1);
        e1.getUcs().add(uc2);
        estudanteRep.save(e1);

        Estudante e2 = new Estudante("Aluno 2", "aluno2@escola.pt", passAluno, "A124");
        e2.getRoles().add(roleEstudante);
        e2.getUcs().add(uc1);
        estudanteRep.save(e2);

        System.out.println("Inseridos: roles, docente, 2 UCs e 2 estudantes.");
        System.out.println("UC1 id: " + uc1.getId());
        System.out.println("UC2 id: " + uc2.getId());
    }
}
