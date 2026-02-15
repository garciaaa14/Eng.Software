package com.example.projetoes.controller.dto;

import java.time.LocalDateTime;

public record EtapaStatusDTO(
        Long etapaId,
        int ordem,
        String descricao,
        boolean concluida,
        LocalDateTime dataConclusao
) {}
