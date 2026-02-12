package com.example.projetoes.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "exercicios")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"uc", "etapas", "estudanteExercicios"})
public class Exercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String enunciado;

    @Column(name="data_criacao", nullable=false, columnDefinition="DATETIME")
    private LocalDateTime dataCriacao;

    @Column(name="data_limite", columnDefinition="DATETIME")
    private LocalDateTime dataLimite;

    @Column(nullable = false)
    private boolean ativo = true;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "uc_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_exercicio_uc"))
    private UC uc;

    @OneToMany(mappedBy = "exercicio", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("ordem ASC")
    private List<Etapa> etapas = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "exercicio", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EstudanteExercicio> estudanteExercicios = new HashSet<>();

    public Exercicio(String titulo, String enunciado, UC uc) {
        this.titulo = titulo;
        this.enunciado = enunciado;
        this.uc = uc;
        this.dataCriacao = LocalDateTime.now();
        this.ativo = true;
    }
}
