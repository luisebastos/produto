package com.example.demo.artigo;

public record RetornarArtigoDTO(
        String id,
        String titulo,
        String descricao,
        String prioridade,
        String emailCriador) {
}
