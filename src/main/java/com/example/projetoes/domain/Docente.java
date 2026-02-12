package com.example.projetoes.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "docentes")
public class Docente extends User {

    @Column(nullable = false, length = 100)
    private String departamento;

    @JsonIgnore
    @OneToMany(mappedBy = "docente", cascade = CascadeType.ALL)
    private Set<UC> ucs = new HashSet<>();

    protected Docente() {
    }

    public Docente(String nome, String email, String passwordHash, String departamento) {
        super(nome, email, passwordHash);
        this.departamento = departamento;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public Set<UC> getUcs() {
        return ucs;
    }

    public void setUcs(Set<UC> ucs) {
        this.ucs = ucs;
    }

    @Override
    public String toString() {
        return "Docente{" +
                "id=" + getId() +
                ", nome='" + getNome() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", departamento='" + departamento + '\'' +
                '}';
    }
}
