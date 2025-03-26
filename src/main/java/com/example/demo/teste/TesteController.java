package com.example.demo.teste;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/curso")
public class TesteController {

//    @GetMapping("/teste")
//    public String teste(){
//        return "Hello World";
//    }

    @Autowired
    private TesteService testeService;

    @GetMapping
    public List<Teste> getTeste() {
        return testeService.getTeste();
    }

    @PostMapping
    public Teste salvarTeste(@RequestBody Teste teste, @RequestHeader(name = "email") String email) {
        return testeService.salvarTeste(teste, email);
    }

    @DeleteMapping("/{id}")
    public void deletarTeste(@PathVariable String id, @RequestHeader(name = "email") String email) {
        testeService.deletarTeste(id, email);
    }
}
