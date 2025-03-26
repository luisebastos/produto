package com.example.demo.produto.test;

import com.example.demo.teste.Teste;
import com.example.demo.teste.TesteController;
import com.example.demo.teste.TesteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TesteControllerTests {

    @InjectMocks
    private TesteController testeController;

    @Mock
    private TesteService testeService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(testeController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();

        this.objectMapper = new ObjectMapper();
    }

    @Test
    void test_GetTestes() throws Exception {
        List<Teste> testes = Arrays.asList(
                criarTeste("Curso A", "Descrição A", "Instrutor A", 20),
                criarTeste("Curso B", "Descrição B", "Instrutor B", 15)
        );

        Mockito.when(testeService.getTeste()).thenReturn(testes);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/curso"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(testes)));
    }


    @Test
    void test_PostTeste() throws Exception {
        Teste teste = criarTeste("Curso C", "Descrição C", "Instrutor C", 30);

        Mockito.when(testeService.salvarTeste(Mockito.any(Teste.class), Mockito.eq("admin@email.com")))
                .thenReturn(teste);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/curso")
                                .header("email", "admin@email.com")
                                .content(objectMapper.writeValueAsString(teste))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(teste)));
    }


    private Teste criarTeste(String titulo, String descricao, String instrutor, Integer cargaHoraria) {
        Teste teste = new Teste();
        teste.setTitulo(titulo);
        teste.setDescricao(descricao);
        teste.setInstrutor(instrutor);
        teste.setCargaHoraria(cargaHoraria);
        return teste;
    }
}

