package br.com.fiap.api_rest.dto;

import br.com.fiap.api_rest.model.Livro;

import java.util.List;

public class AutorResponseDTO {
    private Long id;
    private String nome;
    private List<LivroResponseDTO> livros;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<LivroResponseDTO> getLivros() {
        return livros;
    }

    public void setLivros(List<LivroResponseDTO> livros) {
        this.livros = livros;
    }
} 