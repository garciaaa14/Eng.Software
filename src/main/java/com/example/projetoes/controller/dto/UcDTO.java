package com.example.projetoes.controller.dto;

public record UcDTO(
        Long id,
        String designacao,
        String anoLetivo,
        Long docenteId,
        String docenteNome
) {}
