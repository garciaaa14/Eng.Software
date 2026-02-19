package com.example.projetoes.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "estudante_exercicio_etapas",
        uniqueConstraints = @UniqueConstraint(name = "uk_ee_etapa", columnNames = {"estudante_exercicio_id", "etapa_id"}))
public class EstudanteExercicioEtapa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean concluida = false;

    @Column(name = "data_conclusao", columnDefinition = "DATETIME")
    private LocalDateTime dataConclusao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "estudante_exercicio_id", nullable = false, foreignKey = @ForeignKey(name = "fk_eee_ee"))
    private EstudanteExercicio estudanteExercicio;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "etapa_id", nullable = false, foreignKey = @ForeignKey(name = "fk_eee_etapa"))
    private Etapa etapa;

    protected EstudanteExercicioEtapa() {
    }

    public EstudanteExercicioEtapa(EstudanteExercicio estudanteExercicio, Etapa etapa) {
        this.estudanteExercicio = estudanteExercicio;
        this.etapa = etapa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }

    public LocalDateTime getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDateTime dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public EstudanteExercicio getEstudanteExercicio() {
        return estudanteExercicio;
    }

    public void setEstudanteExercicio(EstudanteExercicio estudanteExercicio) {
        this.estudanteExercicio = estudanteExercicio;
    }

    public Etapa getEtapa() {
        return etapa;
    }

    public void setEtapa(Etapa etapa) {
        this.etapa = etapa;
    }

    @Override
    public String toString() {
        return "EstudanteExercicioEtapa{" +
                "id=" + id +
                ", concluida=" + concluida +
                ", dataConclusao=" + dataConclusao +
                '}';
    }
}
