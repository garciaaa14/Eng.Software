package com.example.projetoes.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "exercicios")
public class Exercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String enunciado;

    @Column(name = "data_criacao", nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime dataCriacao;

    @Column(name = "data_limite", columnDefinition = "DATETIME")
    private LocalDateTime dataLimite;

    @Column(nullable = false)
    private boolean ativo = true;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "uc_id", nullable = false, foreignKey = @ForeignKey(name = "fk_exercicio_uc"))
    private UC uc;

    @OneToMany(mappedBy = "exercicio", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("ordem ASC")
    private List<Etapa> etapas = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "exercicio", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EstudanteExercicio> estudanteExercicios = new HashSet<>();

    protected Exercicio() {
    }

    public Exercicio(String titulo, String enunciado, UC uc) {
        this.titulo = titulo;
        this.enunciado = enunciado;
        this.uc = uc;
        this.dataCriacao = LocalDateTime.now();
        this.ativo = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataLimite() {
        return dataLimite;
    }

    public void setDataLimite(LocalDateTime dataLimite) {
        this.dataLimite = dataLimite;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public UC getUc() {
        return uc;
    }

    public void setUc(UC uc) {
        this.uc = uc;
    }

    public List<Etapa> getEtapas() {
        return etapas;
    }

    public void setEtapas(List<Etapa> etapas) {
        this.etapas = etapas;
    }

    public Set<EstudanteExercicio> getEstudanteExercicios() {
        return estudanteExercicios;
    }

    public void setEstudanteExercicios(Set<EstudanteExercicio> estudanteExercicios) {
        this.estudanteExercicios = estudanteExercicios;
    }

    @Override
    public String toString() {
        return "Exercicio{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", dataCriacao=" + dataCriacao +
                ", dataLimite=" + dataLimite +
                ", ativo=" + ativo +
                '}';
    }
}
