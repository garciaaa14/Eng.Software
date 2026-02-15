package com.example.projetoes.controller;

import com.example.projetoes.controller.dto.UcDTO;
import com.example.projetoes.dao.UCRep;
import com.example.projetoes.domain.UC;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ucs")
public class UCController {

    private final UCRep ucRep;

    public UCController(UCRep ucRep) {
        this.ucRep = ucRep;
    }

    @GetMapping("/docente/{docenteId}")
    public List<UcDTO> ucsDoDocente(@PathVariable Long docenteId) {
        return ucRep.findByDocenteId(docenteId).stream()
                .map(this::toDto)
                .toList();
    }

    @GetMapping("/estudante/{estudanteId}")
    public List<UcDTO> ucsDoEstudante(@PathVariable Long estudanteId) {
        return ucRep.findByEstudanteId(estudanteId).stream()
                .map(this::toDto)
                .toList();
    }

    private UcDTO toDto(UC uc) {
        return new UcDTO(
                uc.getId(),
                uc.getDesignacao(),
                uc.getAnoLetivo(),
                uc.getDocente().getId(),
                uc.getDocente().getNome()
        );
    }
}
