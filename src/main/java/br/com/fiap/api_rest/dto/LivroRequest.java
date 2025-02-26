package br.com.fiap.api_rest.dto;

import br.com.fiap.api_rest.model.Categoria;
import jakarta.validation.constraints.*;

public class LivroRequest {
    @NotBlank(message = "O título é obrigatório")
    @Size(min = 3, max = 100, message = "O título deve ter entre 2 e 100 caracteres")
    private String titulo;
    @NotBlank(message = "O autor é obrigatório")
    @Size(min = 3, max = 100, message = "O autor deve ter entre 2 e 100 caracteres")
    private String autor;
    @Min(value = 10, message = "O preço deve ser maior que 10")
    @Max(value = 1000, message = "O preço deve ser menor que 1000")
    private int preco;
    @Pattern(regexp = "^970\\d{10}$|^970\\d{7}$", message = "O ISBN deve seguir o padrão 970- e 10 ou 13 digitos")
    private String isbn;
    @NotNull(message = "A categoria é obrigatória")
    private Categoria categoria;

    public int getPreco() {
        return preco;
    }

    public void setPreco(int preco) {
        this.preco = preco;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }



    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }
}
