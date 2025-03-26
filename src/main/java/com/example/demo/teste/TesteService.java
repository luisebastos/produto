package com.example.demo.teste;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.example.demo.usuario.Usuario;
import com.example.demo.usuario.UsuarioService;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TesteService {

    @Autowired
    private TesteRepository testeRepository;

    @Autowired
    private UsuarioService usuarioService;

    public Teste salvarTeste(Teste teste, String email){
        Usuario usuario = usuarioService.getUsuario(email);
        if (usuario.getPapel().equalsIgnoreCase("USER")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuário não tem permissão para adicionar cursos.");
        }
        teste.setNomeUsuario(usuario.getNome());
        teste.setEmailUsuario(usuario.getEmail());
        return testeRepository.save(teste);
    }

    public void deletarTeste(String id, String email) {
        Usuario usuario = usuarioService.getUsuario(email);
        if (usuario.getPapel().equalsIgnoreCase("USER")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuário não tem permissão para excluir cursos.");
        }
        testeRepository.deleteById(id);
    }

    public List<Teste> getTeste() {
        return testeRepository.findAll();
    }

}
