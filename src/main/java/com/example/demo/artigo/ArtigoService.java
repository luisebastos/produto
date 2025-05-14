package com.example.demo.artigo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class ArtigoService {

    @Autowired
    private ArtigoRepository artigoRepository;

    public RetornarArtigoDTO criar(CadastrarArtigoDTO dto, String email, List<String> roles) {
        if (!roles.contains("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Artigo artigo = new Artigo();
        artigo.setTitulo(dto.titulo());
        artigo.setDescricao(dto.descricao());
        artigo.setPrioridade(dto.prioridade());
        artigo.setEmailCriador(email);

        artigo = artigoRepository.save(artigo);

        return new RetornarArtigoDTO(artigo.getId(), artigo.getTitulo(), artigo.getDescricao(), artigo.getPrioridade(), artigo.getEmailCriador());
    }

    public void deletar(String id, List<String> roles) {
        if (!roles.contains("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Artigo artigo = artigoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        artigoRepository.delete(artigo);
    }

    public List<Artigo> listar() {
        return artigoRepository.findAll();
    }
}

