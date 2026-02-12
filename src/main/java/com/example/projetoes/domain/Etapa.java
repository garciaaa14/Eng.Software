package com.example.projetoes.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "etapas",
        uniqueConstraints = @UniqueConstraint(name = "uk_etapa_exercicio_ordem", columnNames = {"exercicio_id", "ordem"}))
public class Etapa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 220)
    private String descricao;

    @Column(nullable = false)
    private Integer ordem;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "exercicio_id", nullable = false, foreignKey = @ForeignKey(name = "fk_etapa_exercicio"))
    private Exercicio exercicio;

    protected Etapa() {
        // JPA
    }

    public Etapa(String descricao, Integer ordem, Exercicio exercicio) {
        this.descricao = descricao;
        this.ordem = ordem;
        this.exercicio = exercicio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public Exercicio getExercicio() {
        return exercicio;
    }

    public void setExercicio(Exercicio exercicio) {
        this.exercicio = exercicio;
    }

    @Override
    public String toString() {
        return "Etapa{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", ordem=" + ordem +
                '}';
    }
}
