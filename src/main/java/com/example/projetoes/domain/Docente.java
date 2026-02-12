package com.example.projetoes.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "docentes")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true, exclude = {"ucs"})
public class Docente extends User {

    @Column(nullable = false, length = 100)
    private String departamento;

    @JsonIgnore
    @OneToMany(mappedBy = "docente", cascade = CascadeType.ALL)
    private Set<UC> ucs = new HashSet<>();

    public Docente(String nome, String email, String passwordHash, String departamento) {
        super(nome, email, passwordHash);
        this.departamento = departamento;
    }
}
