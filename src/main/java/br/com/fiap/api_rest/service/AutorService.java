package br.com.fiap.api_rest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import br.com.fiap.api_rest.controller.AutorController;
import br.com.fiap.api_rest.dto.AutorRequest;
import br.com.fiap.api_rest.dto.AutorResponse;
import br.com.fiap.api_rest.dto.AutorResponseDTO;
import br.com.fiap.api_rest.dto.LivroResponseDTO;
import br.com.fiap.api_rest.model.Autor;
import br.com.fiap.api_rest.repository.AutorRepository;

@Service
public class AutorService {
    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private LivroService livroService;

    public Autor requestToAutor(AutorRequest autorRequest) {
        return new Autor(autorRequest.nome());
    }

    public AutorResponse autorToResponse(Autor autor) {
        return new AutorResponse(
                autor.getId(),
                autor.getNome(),
                livroService.livrosToResponse(autor.getLivros()));
    }

    public AutorResponseDTO autorToResponseDTO(Autor autor, boolean self) {
        Link link;
        if (self) {
            link = linkTo(methodOn(AutorController.class).buscarPorId(autor.getId())).withSelfRel();
        } else {
            link = linkTo(methodOn(AutorController.class).listar(0)).withRel("Lista de Autores");
        }
        
        AutorResponseDTO dto = new AutorResponseDTO();
        dto.setId(autor.getId());
        dto.setNome(autor.getNome());
        
        if (autor.getLivros() != null) {
            List<LivroResponseDTO> livrosDTO = autor.getLivros().stream()
                    .map(livro -> livroService.livroToResponseDTO(livro, false))
                    .collect(Collectors.toList());
            dto.setLivros(livrosDTO);
        } else {
            dto.setLivros(new ArrayList<>());
        }
        
        return dto;
    }

    public AutorResponse save(AutorRequest autorRequest) {
        return autorToResponse(autorRepository.save(requestToAutor(autorRequest)));
    }

    public Autor save(Autor autor) {
        return autorRepository.save(autor);
    }

    public Page<AutorResponse> findAll(Pageable pageable) {
        return autorRepository.findAll(pageable).map(this::autorToResponse);
    }

    public Page<AutorResponseDTO> findAllDTO(Pageable pageable) {
        return autorRepository.findAll(pageable).map(autor -> autorToResponseDTO(autor, true));
    }

    public Optional<Autor> findById(Long id) {
        return autorRepository.findById(id);
    }

    public AutorResponse update(AutorRequest autorRequest, Long id) {
        Optional<Autor> autor = autorRepository.findById(id);
        if (autor.isPresent()) {
            Autor existingAutor = autor.get();
            existingAutor.setNome(autorRequest.nome());
            Autor autorSalvo = autorRepository.save(existingAutor);
            return autorToResponse(autorSalvo);
        }
        return null;
    }

    public boolean delete(Long id) {
        Optional<Autor> autor = autorRepository.findById(id);
        if (autor.isPresent()) {
            autorRepository.delete(autor.get());
            return true;
        }
        return false;
    }

    public void deleteById(Long id) {
        autorRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return autorRepository.existsById(id);
    }
} 