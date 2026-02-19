package com.example.projetoes.controller;

import com.example.projetoes.dao.*;
import com.example.projetoes.domain.*;
import com.example.projetoes.service.ExercicioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ExercicioControllerWebTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper om;

    @Autowired UCRep ucRep;
    @Autowired ExercicioService exercicioService;
    @Autowired EstudanteExercicioRep eeRep;
    @Autowired EstudanteExercicioEtapaRep eeeRep;

    private Long ucId;

    @BeforeEach
    void setup() {
        ucId = ucRep.findByDesignacao("Engenharia de Software").orElseThrow().getId();
    }

    @Test
    @WithMockUser(roles = "DOCENTE")
    void docente_podeCriarExercicio() throws Exception {
        String body = om.writeValueAsString(Map.of(
                "titulo", "Ex MVC",
                "enunciado", "Teste",
                "etapas", List.of("E1", "E2")
        ));

        mockMvc.perform(post("/api/exercicios/uc/{ucId}", ucId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.titulo").value("Ex MVC"));
    }

    @Test
    @WithMockUser(roles = "ESTUDANTE")
    void estudante_naoPodeCriarExercicio() throws Exception {
        String body = om.writeValueAsString(Map.of(
                "titulo", "Ex proibido",
                "enunciado", "Teste",
                "etapas", List.of("E1")
        ));

        mockMvc.perform(post("/api/exercicios/uc/{ucId}", ucId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ESTUDANTE")
    void estudante_podeConcluirEtapa() throws Exception {
        Exercicio e = exercicioService.criarExercicio(ucId, "Ex Concluir", "Enun", List.of("E1", "E2"));

        EstudanteExercicio ee = eeRep.findByExercicioId(e.getId()).get(0);
        Long etapaId = e.getEtapas().get(0).getId();

        mockMvc.perform(patch("/api/exercicios/execucao/{eeId}/etapa/{etapaId}/concluir", ee.getId(), etapaId))
                .andExpect(status().isOk());

        EstudanteExercicioEtapa status =
                eeeRep.findByEeIdAndEtapaId(ee.getId(), etapaId).orElseThrow();

        assertThat(status.isConcluida()).isTrue();
        assertThat(status.getDataConclusao()).isNotNull();
    }
}
