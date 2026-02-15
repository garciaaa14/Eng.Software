package com.example.projetoes.controller.dto;

public record EstudanteExercicioDTO(
        Long id,
        Long estudanteId,
        String estudanteNome,
        String estudanteNumero,
        Long exercicioId,
        String exercicioTitulo,
        boolean chamouDocente,
        boolean terminado,
        Double nota
) {}
