package com.example.projetoes.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "ucs")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"docente", "alunos", "exercicios"})
public class UC {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, length = 160)
    private String designacao;

    @Column(name = "ano_letivo", nullable = false, length = 20)
    private String anoLetivo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "docente_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_uc_docente"))
    private Docente docente;

    @JsonIgnore
    @ManyToMany(mappedBy = "ucs")
    private Set<Estudante> alunos = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "uc", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Exercicio> exercicios = new LinkedList<>();

    public UC(String designacao, String anoLetivo, Docente docente) {
        this.designacao = designacao;
        this.anoLetivo = anoLetivo;
        this.docente = docente;
    }
}
