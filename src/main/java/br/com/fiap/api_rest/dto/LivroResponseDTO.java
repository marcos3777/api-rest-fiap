package br.com.fiap.api_rest.dto;

import br.com.fiap.api_rest.model.Autor;
import br.com.fiap.api_rest.model.Biblioteca;
import br.com.fiap.api_rest.model.Categoria;

import java.util.List;

public class LivroResponseDTO {
    private Long id;
    private String titulo;
    private Integer preco;
    private Categoria categoria;
    private String isbn;
    private List<Autor> autores;
    private Biblioteca biblioteca;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getPreco() {
        return preco;
    }

    public void setPreco(Integer preco) {
        this.preco = preco;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public Biblioteca getBiblioteca() {
        return biblioteca;
    }

    public void setBiblioteca(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }
}
