package com.example.demo.artigo;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Artigo {
    @Id
    private String id;
    private String titulo;
    private String descricao;
    private String prioridade;
    private String emailCriador;

    // Getters e Setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPrioridade() {
        return prioridade;
    }
    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public String getEmailCriador() {
        return emailCriador;
    }
    public void setEmailCriador(String emailCriador) {
        this.emailCriador = emailCriador;
    }
}
