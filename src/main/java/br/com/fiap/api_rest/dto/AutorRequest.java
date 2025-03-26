package br.com.fiap.api_rest.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

public record AutorRequest(
        @NotBlank(message = "O nome do autor é obrigatório") String nome
) {
    public String getNome() {
        return nome;
    }

    public List<Long> getLivroIds() {
        return List.of();
    }
} 