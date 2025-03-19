package br.com.fiap.api_rest.model;


import jakarta.persistence.*;

import java.util.List;

@Entity
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;
    @ManyToMany(cascade = CascadeType.ALL)
    //tabela autor_livro
    //colunas id_livro, id_autor
    // 1, 1
    // 1, 2
    // 5, 2
    @JoinTable(name = "autor_livro",
            joinColumns = @JoinColumn(name = "id_livro", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_autor", referencedColumnName = "id"))
    private List<Livro> livros;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



}
