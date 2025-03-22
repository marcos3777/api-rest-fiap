package br.com.fiap.api_rest.service;

import br.com.fiap.api_rest.dto.AutorRequest;
import br.com.fiap.api_rest.dto.AutorResponseDTO;
import br.com.fiap.api_rest.dto.LivroResponseDTO;
import br.com.fiap.api_rest.model.Autor;
import br.com.fiap.api_rest.model.Livro;
import br.com.fiap.api_rest.repository.AutorRepository;
import br.com.fiap.api_rest.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AutorService {
    @Autowired
    private AutorRepository autorRepository;
    
    @Autowired
    private LivroService livroService;
    
    @Autowired
    private LivroRepository livroRepository;

    public Autor requestToAutor(AutorRequest request) {
        Autor autor = new Autor();
        autor.setNome(request.getNome());
        
        if (request.getLivroIds() != null && !request.getLivroIds().isEmpty()) {
            List<Livro> livros = livroRepository.findAllById(request.getLivroIds());
            autor.setLivros(livros);
        } else {
            autor.setLivros(new ArrayList<>());
        }
        
        return autor;
    }

    public AutorResponseDTO autorToResponseDTO(Autor autor, boolean showDetails) {
        AutorResponseDTO dto = new AutorResponseDTO();
        dto.setId(autor.getId());
        dto.setNome(autor.getNome());
        
        if (showDetails && autor.getLivros() != null) {
            dto.setLivros(autor.getLivros().stream()
                    .map(livro -> livroService.livroToResponseDTO(livro, false))
                    .collect(Collectors.toList()));
        }
        
        return dto;
    }

    public Page<AutorResponseDTO> findAllDTO(Pageable pageable) {
        Page<Autor> autores = autorRepository.findAll(pageable);
        return autores.map(autor -> autorToResponseDTO(autor, true));
    }

    public Autor save(Autor autor) {
        return autorRepository.save(autor);
    }

    public Optional<Autor> findById(Long id) {
        return autorRepository.findById(id);
    }

    public boolean existsById(Long id) {
        return autorRepository.existsById(id);
    }

    public void deleteById(Long id) {
        autorRepository.deleteById(id);
    }
} 