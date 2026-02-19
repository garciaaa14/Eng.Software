package com.example.projetoes.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "estudante_exercicios",
        uniqueConstraints = @UniqueConstraint(name = "uk_estudante_exercicio", columnNames = {"estudante_id", "exercicio_id"}))
public class EstudanteExercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean terminado = false;

    @Column(columnDefinition = "DECIMAL(5,2)")
    private Double nota;

    @Column(name = "chamou_docente", nullable = false)
    private boolean chamouDocente = false;

    @Column(name = "data_chamada", columnDefinition = "DATETIME")
    private LocalDateTime dataChamada;

    @Column(name = "data_termino", columnDefinition = "DATETIME")
    private LocalDateTime dataTermino;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "estudante_id", nullable = false, foreignKey = @ForeignKey(name = "fk_ee_estudante"))
    private Estudante estudante;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "exercicio_id", nullable = false, foreignKey = @ForeignKey(name = "fk_ee_exercicio"))
    private Exercicio exercicio;

    @JsonIgnore
    @OneToMany(mappedBy = "estudanteExercicio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EstudanteExercicioEtapa> etapasStatus = new ArrayList<>();

    protected EstudanteExercicio() {
    }

    public EstudanteExercicio(Estudante estudante, Exercicio exercicio) {
        this.estudante = estudante;
        this.exercicio = exercicio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isTerminado() {
        return terminado;
    }

    public void setTerminado(boolean terminado) {
        this.terminado = terminado;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public boolean isChamouDocente() {
        return chamouDocente;
    }

    public void setChamouDocente(boolean chamouDocente) {
        this.chamouDocente = chamouDocente;
    }

    public LocalDateTime getDataChamada() {
        return dataChamada;
    }

    public void setDataChamada(LocalDateTime dataChamada) {
        this.dataChamada = dataChamada;
    }

    public LocalDateTime getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(LocalDateTime dataTermino) {
        this.dataTermino = dataTermino;
    }

    public Estudante getEstudante() {
        return estudante;
    }

    public void setEstudante(Estudante estudante) {
        this.estudante = estudante;
    }

    public Exercicio getExercicio() {
        return exercicio;
    }

    public void setExercicio(Exercicio exercicio) {
        this.exercicio = exercicio;
    }

    public List<EstudanteExercicioEtapa> getEtapasStatus() {
        return etapasStatus;
    }

    public void setEtapasStatus(List<EstudanteExercicioEtapa> etapasStatus) {
        this.etapasStatus = etapasStatus;
    }

    @Override
    public String toString() {
        return "EstudanteExercicio{" +
                "id=" + id +
                ", terminado=" + terminado +
                ", nota=" + nota +
                ", chamouDocente=" + chamouDocente +
                ", dataChamada=" + dataChamada +
                ", dataTermino=" + dataTermino +
                '}';
    }
}
