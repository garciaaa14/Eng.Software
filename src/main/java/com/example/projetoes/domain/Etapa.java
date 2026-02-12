package com.example.projetoes.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "etapas",
        uniqueConstraints = @UniqueConstraint(name = "uk_etapa_exercicio_ordem", columnNames = {"exercicio_id", "ordem"}))
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"exercicio"})
public class Etapa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, length = 220)
    private String descricao;

    @Column(nullable = false)
    private Integer ordem;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "exercicio_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_etapa_exercicio"))
    private Exercicio exercicio;

    public Etapa(String descricao, Integer ordem, Exercicio exercicio) {
        this.descricao = descricao;
        this.ordem = ordem;
        this.exercicio = exercicio;
    }
}
