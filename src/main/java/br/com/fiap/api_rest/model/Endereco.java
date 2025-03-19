package br.com.fiap.api_rest.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity



    public class Endereco{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private String localizacao;

    public Biblioteca getBiblioteca() {
        return biblioteca;
    }

    public void setBiblioteca(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @OneToOne(mappedBy = "endereco")
        private Biblioteca biblioteca;
    }

