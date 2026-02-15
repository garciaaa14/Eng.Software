package com.example.projetoes.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class ExercicioCreateDTO {
    @NotBlank
    public String titulo;

    @NotBlank
    public String enunciado;

    @NotEmpty
    public List<String> etapas;
}
