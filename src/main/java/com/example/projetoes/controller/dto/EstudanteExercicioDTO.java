package com.example.projetoes.controller.dto;

public record EstudanteExercicioDTO(
        Long id,
        Long estudanteId,
        String estudanteNome,
        String estudanteNumero,
        Long exercicioId,
        String exercicioTitulo,
        Long ucId,
        String ucDesignacao,
        boolean chamouDocente,
        boolean terminado,
        Double nota
) {}
