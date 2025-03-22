package br.com.fiap.api_rest.service;

import br.com.fiap.api_rest.dto.BibliotecaRequest;
import br.com.fiap.api_rest.dto.BibliotecaResponseDTO;
import br.com.fiap.api_rest.model.Biblioteca;
import br.com.fiap.api_rest.repository.BibliotecaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BibliotecaService {
    @Autowired
    private BibliotecaRepository bibliotecaRepository;
    
    @Autowired
    private LivroService livroService;

    public Biblioteca requestToBiblioteca(BibliotecaRequest request) {
        Biblioteca biblioteca = new Biblioteca();
        biblioteca.setNome(request.getNome());
        biblioteca.setEndereco(request.getEndereco());
        return biblioteca;
    }

    public BibliotecaResponseDTO bibliotecaToResponseDTO(Biblioteca biblioteca, boolean showDetails) {
        BibliotecaResponseDTO dto = new BibliotecaResponseDTO();
        dto.setId(biblioteca.getId());
        dto.setNome(biblioteca.getNome());
        dto.setEndereco(biblioteca.getEndereco());
        
        if (showDetails && biblioteca.getLivros() != null) {
            dto.setLivros(biblioteca.getLivros().stream()
                    .map(livro -> livroService.livroToResponseDTO(livro, false))
                    .collect(Collectors.toList()));
        }
        
        return dto;
    }

    public Page<BibliotecaResponseDTO> findAllDTO(Pageable pageable) {
        Page<Biblioteca> bibliotecas = bibliotecaRepository.findAll(pageable);
        return bibliotecas.map(biblioteca -> bibliotecaToResponseDTO(biblioteca, true));
    }

    public Biblioteca save(Biblioteca biblioteca) {
        return bibliotecaRepository.save(biblioteca);
    }

    public Optional<Biblioteca> findById(Long id) {
        return bibliotecaRepository.findById(id);
    }

    public boolean existsById(Long id) {
        return bibliotecaRepository.existsById(id);
    }

    public void deleteById(Long id) {
        bibliotecaRepository.deleteById(id);
    }
} 