package com.example.projetoes.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "ucs")
public class UC {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 160)
    private String designacao;

    @Column(name = "ano_letivo", nullable = false, length = 20)
    private String anoLetivo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "docente_id", nullable = false, foreignKey = @ForeignKey(name = "fk_uc_docente"))
    private Docente docente;

    @JsonIgnore
    @ManyToMany(mappedBy = "ucs")
    private Set<Estudante> alunos = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "uc", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Exercicio> exercicios = new LinkedList<>();

    protected UC() {
    }

    public UC(String designacao, String anoLetivo, Docente docente) {
        this.designacao = designacao;
        this.anoLetivo = anoLetivo;
        this.docente = docente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesignacao() {
        return designacao;
    }

    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }

    public String getAnoLetivo() {
        return anoLetivo;
    }

    public void setAnoLetivo(String anoLetivo) {
        this.anoLetivo = anoLetivo;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public Set<Estudante> getAlunos() {
        return alunos;
    }

    public void setAlunos(Set<Estudante> alunos) {
        this.alunos = alunos;
    }

    public List<Exercicio> getExercicios() {
        return exercicios;
    }

    public void setExercicios(List<Exercicio> exercicios) {
        this.exercicios = exercicios;
    }

    @Override
    public String toString() {
        return "UC{" +
                "id=" + id +
                ", designacao='" + designacao + '\'' +
                ", anoLetivo='" + anoLetivo + '\'' +
                '}';
    }
}
