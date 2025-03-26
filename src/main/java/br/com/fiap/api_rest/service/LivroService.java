package br.com.fiap.api_rest.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import br.com.fiap.api_rest.controller.LivroController;
import br.com.fiap.api_rest.dto.AutorRequest;
import br.com.fiap.api_rest.dto.LivroRequest;
import br.com.fiap.api_rest.dto.LivroResponse;
import br.com.fiap.api_rest.dto.LivroResponseDTO;
import br.com.fiap.api_rest.model.Autor;
import br.com.fiap.api_rest.model.Livro;
import br.com.fiap.api_rest.repository.LivroRepository;

@Service
public class LivroService {
    @Autowired
    private LivroRepository livroRepository;
    @Autowired
    private AutorService autorService;

    public Livro requestToLivro(LivroRequest livroRequest) {
        Livro livro = new Livro();
        livro.setTitulo(livroRequest.getTitulo());
        livro.setPreco(livroRequest.getPreco());
        livro.setCategoria(livroRequest.getCategoria());
        livro.setIsbn(livroRequest.getIsbn());
        
        // Handle authors separately
        if (livroRequest.getAutores() != null) {
            List<Autor> autores = new ArrayList<>();
            for (AutorRequest autorRequest : livroRequest.getAutores()) {
                Autor autor = autorService.requestToAutor(autorRequest);
                autores.add(autor);
            }
            livro.setAutores(autores);
        }
        
        // Handle biblioteca if present
        if (livroRequest.getBiblioteca() != null) {
            livro.setBiblioteca(livroRequest.getBiblioteca());
        }
        
        return livro;
    }

    public LivroResponse livroToResponse(Livro livro) {
        String infoLivro = livro.getTitulo();
        if (livro.getAutores() != null && !livro.getAutores().isEmpty()) {
            infoLivro = livro.getAutores().stream()
                    .map(Autor::getNome)
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("") + " - " + livro.getTitulo();
        }
        return new LivroResponse(livro.getId(), infoLivro);
    }

    public LivroResponseDTO livroToResponseDTO(Livro livro, boolean self) {
        Link link;
        if (self) {
            link = linkTo(methodOn(LivroController.class).readLivro(livro.getId())).withSelfRel();
        } else {
            link = linkTo(methodOn(LivroController.class).readLivros(0)).withRel("Lista de Livros");
        }
        String infoLivro = livro.getTitulo();
        if (livro.getAutores() != null && !livro.getAutores().isEmpty()) {
            infoLivro = livro.getAutores().stream()
                    .map(Autor::getNome)
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("") + " - " + livro.getTitulo();
        }
        return new LivroResponseDTO(livro.getId(), infoLivro, link);
    }

    public List<LivroResponse> livrosToResponse(List<Livro> livros) {
        List<LivroResponse> listaLivros = new ArrayList<>();
        for (Livro livro : livros) {
            listaLivros.add(livroToResponse(livro));
        }
        return listaLivros;
    }

    public Page<LivroResponse> findAll(Pageable pageable) {
        return livroRepository.findAll(pageable).map(this::livroToResponse);
    }

    public Page<LivroResponseDTO> findAllDTO(Pageable pageable) {
        return livroRepository.findAll(pageable).map(livro -> livroToResponseDTO(livro, true));
    }
}
