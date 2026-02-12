package com.example.projetoes.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "estudantes",
        uniqueConstraints = @UniqueConstraint(name = "uk_estudante_num", columnNames = "numero"))
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true, exclude = {"ucs", "estudanteExercicios"})
public class Estudante extends User {

    @Column(name = "numero", nullable = false, length = 40)
    private String numero;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "uc_estudantes",
            joinColumns = @JoinColumn(name = "estudante_id"),
            inverseJoinColumns = @JoinColumn(name = "uc_id"),
            uniqueConstraints = @UniqueConstraint(name = "uk_uc_estudante", columnNames = {"estudante_id", "uc_id"})
    )
    private Set<UC> ucs = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "estudante", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EstudanteExercicio> estudanteExercicios = new HashSet<>();

    public Estudante(String nome, String email, String passwordHash, String numero) {
        super(nome, email, passwordHash);
        this.numero = numero;
    }
}
