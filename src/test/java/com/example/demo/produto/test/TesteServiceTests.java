package com.example.demo.teste;

import com.example.demo.usuario.Usuario;
import com.example.demo.usuario.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TesteServiceTests {

    private TesteRepository testeRepository;
    private UsuarioService usuarioService;
    private TesteService testeService;

    @BeforeEach
    void setup() {
        testeRepository = mock(TesteRepository.class);
        usuarioService = mock(UsuarioService.class);
        testeService = new TesteService();
        testeService = Mockito.spy(testeService);
        testeService = new TesteService();
        testeService = new TesteService() {{
            setTesteRepository(testeRepository);
            setUsuarioService(usuarioService);
        }};
    }

    @Test
    void test_SalvarTeste_ComPermissao() {
        Teste teste = new Teste();
        teste.setTitulo("Java");
        teste.setInstrutor("Ana");

        Usuario usuario = new Usuario();
        usuario.setNome("Ana");
        usuario.setEmail("ana@email.com");
        usuario.setPapel("ADMIN");

        when(usuarioService.getUsuario("ana@email.com")).thenReturn(usuario);
        when(testeRepository.save(teste)).thenReturn(teste);

        Teste resultado = testeService.salvarTeste(teste, "ana@email.com");

        assertEquals("Ana", resultado.getNomeUsuario());
        assertEquals("ana@email.com", resultado.getEmailUsuario());
    }

    @Test
    void test_SalvarTeste_SemPermissao() {
        Teste teste = new Teste();

        Usuario usuario = new Usuario();
        usuario.setNome("User");
        usuario.setEmail("user@email.com");
        usuario.setPapel("USER");

        when(usuarioService.getUsuario("user@email.com")).thenReturn(usuario);

        assertThrows(ResponseStatusException.class, () -> {
            testeService.salvarTeste(teste, "user@email.com");
        });
    }

    @Test
    void test_DeletarTeste_ComPermissao() {
        Usuario usuario = new Usuario();
        usuario.setPapel("ADMIN");

        when(usuarioService.getUsuario("admin@email.com")).thenReturn(usuario);
        doNothing().when(testeRepository).deleteById("123");

        assertDoesNotThrow(() -> testeService.deletarTeste("123", "admin@email.com"));
    }

    @Test
    void test_DeletarTeste_SemPermissao() {
        Usuario usuario = new Usuario();
        usuario.setPapel("USER");

        when(usuarioService.getUsuario("user@email.com")).thenReturn(usuario);

        assertThrows(ResponseStatusException.class, () -> {
            testeService.deletarTeste("123", "user@email.com");
        });
    }

    @Test
    void test_GetTeste() {
        when(testeRepository.findAll()).thenReturn(Collections.emptyList());

        List<Teste> testes = testeService.getTeste();

        assertTrue(testes.isEmpty());
    }
}

