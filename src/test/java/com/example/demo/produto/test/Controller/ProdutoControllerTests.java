package com.example.demo.produto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ProdutoControllerTests {

    @InjectMocks
    private ProdutoController produtoController;

    @Mock
    private ProdutoService produtoService;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(produtoController).build();
    }

    @Test
    void test_CriarProduto() throws Exception {
        Produto produto = new Produto("1", "Celular", 10);

        Mockito.when(produtoService.criarProduto(Mockito.any(Produto.class)))
                .thenReturn(produto);

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/produto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(produto)));
    }

    @Test
    void test_BuscarProduto() throws Exception {
        Produto produto = new Produto("1", "Celular", 10);

        Mockito.when(produtoService.buscarProdutoPorId("1"))
                .thenReturn(produto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/produto/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(produto)));
    }

    @Test
    void test_ListarProdutos() throws Exception {
        List<Produto> produtos = Arrays.asList(
                new Produto("1", "Celular", 10),
                new Produto("2", "Notebook", 5)
        );

        Mockito.when(produtoService.listarProdutos()).thenReturn(produtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/produto"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(produtos)));
    }

    @Test
    void test_DiminuirEstoque() throws Exception {
        Mockito.doNothing().when(produtoService).diminuirQuantidadeEstoque("1", 2);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/produto/1/diminuir/2"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
