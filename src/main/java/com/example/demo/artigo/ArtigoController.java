package com.example.demo.artigo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import java.util.List;

@RestController
@RequestMapping("/artigos")
public class ArtigoController {

    @Autowired
    private ArtigoService artigoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RetornarArtigoDTO criar(@AuthenticationPrincipal Jwt jwt, @RequestBody CadastrarArtigoDTO dto) {
        String email = jwt.getClaimAsString("https://musica-insper.com/email");
        List<String> roles = jwt.getClaimAsStringList("https://musica-insper.com/roles");
        return artigoService.criar(dto, email, roles);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@AuthenticationPrincipal Jwt jwt, @PathVariable String id) {
        List<String> roles = jwt.getClaimAsStringList("https://musica-insper.com/roles");
        artigoService.deletar(id, roles);
    }

    @GetMapping
    public List<Artigo> listar() {
        return artigoService.listar();
    }
}
