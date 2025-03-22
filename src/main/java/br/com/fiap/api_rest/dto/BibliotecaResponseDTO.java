package br.com.fiap.api_rest.dto;

import br.com.fiap.api_rest.model.Endereco;
import br.com.fiap.api_rest.model.Livro;

import java.util.List;

public class BibliotecaResponseDTO {
    private Long id;
    private String nome;
    private Endereco endereco;
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

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<LivroResponseDTO> getLivros() {
        return livros;
    }

    public void setLivros(List<LivroResponseDTO> livros) {
        this.livros = livros;
    }
} 