package com.example.demo.produto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTests {

    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    @Test
    void test_CriarProduto_Sucesso() {
        Produto produto = new Produto();
        produto.setId("1");
        produto.setNome("Celular");
        produto.setQuantidadeEstoque(10);

        Mockito.when(produtoRepository.save(produto)).thenReturn(produto);

        Produto produtoCriado = produtoService.criarProduto(produto);

        Assertions.assertEquals("Celular", produtoCriado.getNome());
        Assertions.assertEquals(10, produtoCriado.getQuantidadeEstoque());
    }

    @Test
    void test_BuscarProdutoPorId_Sucesso() {
        Produto produto = new Produto();
        produto.setId("1");
        produto.setNome("Celular");
        produto.setQuantidadeEstoque(10);

        Mockito.when(produtoRepository.findById("1")).thenReturn(Optional.of(produto));

        Produto resultado = produtoService.buscarProdutoPorId("1");

        Assertions.assertEquals(produto.getNome(), resultado.getNome());
    }

    @Test
    void test_BuscarProdutoPorId_NaoEncontrado() {
        Mockito.when(produtoRepository.findById("1")).thenReturn(Optional.empty());

        ResponseStatusException exception = Assertions.assertThrows(
                ResponseStatusException.class,
                () -> produtoService.buscarProdutoPorId("1")
        );

        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void test_ListarProdutos_Sucesso() {
        Produto produto1 = new Produto();
        produto1.setId("1");
        produto1.setNome("Celular");
        produto1.setQuantidadeEstoque(10);

        Produto produto2 = new Produto();
        produto2.setId("2");
        produto2.setNome("Notebook");
        produto2.setQuantidadeEstoque(5);

        List<Produto> produtos = Arrays.asList(produto1, produto2);

        Mockito.when(produtoRepository.findAll()).thenReturn(produtos);

        List<Produto> resultado = produtoService.listarProdutos();

        Assertions.assertEquals(2, resultado.size());
    }

    @Test
    void test_DiminuirQuantidadeEstoque_Sucesso() {
        Produto produto = new Produto();
        produto.setId("1");
        produto.setNome("Celular");
        produto.setQuantidadeEstoque(10);

        Mockito.when(produtoRepository.findById("1")).thenReturn(Optional.of(produto));

        produtoService.diminuirQuantidadeEstoque("1", 5);

        Assertions.assertEquals(5, produto.getQuantidadeEstoque());
    }

    @Test
    void test_DiminuirQuantidadeEstoque_EstoqueInsuficiente() {
        Produto produto = new Produto();
        produto.setId("1");
        produto.setNome("Celular");
        produto.setQuantidadeEstoque(2);

        Mockito.when(produtoRepository.findById("1")).thenReturn(Optional.of(produto));

        ResponseStatusException exception = Assertions.assertThrows(
                ResponseStatusException.class,
                () -> produtoService.diminuirQuantidadeEstoque("1", 5)
        );

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }
}
