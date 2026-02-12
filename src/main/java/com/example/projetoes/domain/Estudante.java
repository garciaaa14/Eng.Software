package com.example.projetoes.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "estudantes",
        uniqueConstraints = @UniqueConstraint(name = "uk_estudante_num", columnNames = "numero"))
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

    protected Estudante() {
        // JPA
    }

    public Estudante(String nome, String email, String passwordHash, String numero) {
        super(nome, email, passwordHash);
        this.numero = numero;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Set<UC> getUcs() {
        return ucs;
    }

    public void setUcs(Set<UC> ucs) {
        this.ucs = ucs;
    }

    public Set<EstudanteExercicio> getEstudanteExercicios() {
        return estudanteExercicios;
    }

    public void setEstudanteExercicios(Set<EstudanteExercicio> estudanteExercicios) {
        this.estudanteExercicios = estudanteExercicios;
    }

    @Override
    public String toString() {
        return "Estudante{" +
                "id=" + getId() +
                ", nome='" + getNome() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", numero='" + numero + '\'' +
                '}';
    }
}
