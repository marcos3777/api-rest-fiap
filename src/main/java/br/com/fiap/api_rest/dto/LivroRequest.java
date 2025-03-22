package br.com.fiap.api_rest.dto;

import br.com.fiap.api_rest.model.Autor;
import br.com.fiap.api_rest.model.Biblioteca;
import br.com.fiap.api_rest.model.Categoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public class LivroRequest {
    @NotBlank(message = "O título é obrigatório")
    private String titulo;
    
    @NotNull(message = "O preço é obrigatório")
    @Positive(message = "O preço deve ser positivo")
    private Integer preco;
    
    @NotNull(message = "A categoria é obrigatória")
    private Categoria categoria;
    
    @NotBlank(message = "O ISBN é obrigatório")
    private String isbn;
    
    private List<Autor> autores;
    private Biblioteca biblioteca;

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
