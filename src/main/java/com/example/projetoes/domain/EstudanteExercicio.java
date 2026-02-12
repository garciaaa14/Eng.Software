package com.example.projetoes.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "estudante_exercicios",
        uniqueConstraints = @UniqueConstraint(name = "uk_estudante_exercicio", columnNames = {"estudante_id", "exercicio_id"}))
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"estudante", "exercicio", "etapasStatus"})
public class EstudanteExercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private boolean terminado = false;

    @Column(columnDefinition = "DECIMAL(5,2)")
    private Double nota;

    @Column(name = "chamou_docente", nullable = false)
    private boolean chamouDocente = false;

    @Column(name="data_chamada", columnDefinition="DATETIME")
    private LocalDateTime dataChamada;

    @Column(name="data_termino", columnDefinition="DATETIME")
    private LocalDateTime dataTermino;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "estudante_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_ee_estudante"))
    private Estudante estudante;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "exercicio_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_ee_exercicio"))
    private Exercicio exercicio;

    @JsonIgnore
    @OneToMany(mappedBy = "estudanteExercicio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EstudanteExercicioEtapa> etapasStatus = new ArrayList<>();

    public EstudanteExercicio(Estudante estudante, Exercicio exercicio) {
        this.estudante = estudante;
        this.exercicio = exercicio;
    }
}
