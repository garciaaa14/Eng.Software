package com.example.projetoes.service.dto;

public record ProgressoAlunoDTO(
        Long estudanteExercicioId,
        Long estudanteId,
        String estudanteNome,
        String estudanteNumero,
        long totalEtapas,
        long etapasConcluidas,
        int etapaAtual,
        boolean chamouDocente,
        boolean terminado,
        Double nota
) {}