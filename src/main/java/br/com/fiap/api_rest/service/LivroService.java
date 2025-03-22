package br.com.fiap.api_rest.service;

import br.com.fiap.api_rest.dto.LivroRequest;
import br.com.fiap.api_rest.dto.LivroResponseDTO;
import br.com.fiap.api_rest.model.Livro;
import br.com.fiap.api_rest.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LivroService {
    @Autowired
    private LivroRepository livroRepository;

    public Livro requestToLivro(LivroRequest request) {
        Livro livro = new Livro();
        livro.setTitulo(request.getTitulo());
        livro.setPreco(request.getPreco());
        livro.setCategoria(request.getCategoria());
        livro.setIsbn(request.getIsbn());
        livro.setAutores(request.getAutores());
        livro.setBiblioteca(request.getBiblioteca());
        return livro;
    }

    public LivroResponseDTO livroToResponseDTO(Livro livro, boolean showDetails) {
        LivroResponseDTO dto = new LivroResponseDTO();
        dto.setId(livro.getId());
        dto.setTitulo(livro.getTitulo());
        dto.setPreco(livro.getPreco());
        dto.setCategoria(livro.getCategoria());
        dto.setIsbn(livro.getIsbn());
        
        if (showDetails) {
            dto.setAutores(livro.getAutores());
            dto.setBiblioteca(livro.getBiblioteca());
        }
        
        return dto;
    }

    public Page<LivroResponseDTO> findAllDTO(Pageable pageable) {
        Page<Livro> livros = livroRepository.findAll(pageable);
        return livros.map(livro -> livroToResponseDTO(livro, true));
    }
}
