package com.example.projetoes.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "estudante_exercicio_etapas",
        uniqueConstraints = @UniqueConstraint(name = "uk_ee_etapa", columnNames = {"estudante_exercicio_id", "etapa_id"}))
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"estudanteExercicio", "etapa"})
public class EstudanteExercicioEtapa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private boolean concluida = false;

    @Column(name="data_conclusao", columnDefinition="DATETIME")
    private LocalDateTime dataConclusao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "estudante_exercicio_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_eee_ee"))
    private EstudanteExercicio estudanteExercicio;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "etapa_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_eee_etapa"))
    private Etapa etapa;

    public EstudanteExercicioEtapa(EstudanteExercicio estudanteExercicio, Etapa etapa) {
        this.estudanteExercicio = estudanteExercicio;
        this.etapa = etapa;
    }
}
