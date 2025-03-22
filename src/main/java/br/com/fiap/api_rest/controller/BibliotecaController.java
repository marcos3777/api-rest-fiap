package br.com.fiap.api_rest.controller;

import br.com.fiap.api_rest.dto.BibliotecaRequest;
import br.com.fiap.api_rest.dto.BibliotecaResponseDTO;
import br.com.fiap.api_rest.model.Biblioteca;
import br.com.fiap.api_rest.service.BibliotecaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/bibliotecas")
@Tag(name = "api-bibliotecas")
public class BibliotecaController {

    @Autowired
    private BibliotecaService bibliotecaService;

    @Operation(summary = "Cria uma nova biblioteca")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Biblioteca criada com sucesso",
                    content = @Content(schema = @Schema(implementation = BibliotecaResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema()))
    })
    @PostMapping
    public ResponseEntity<BibliotecaResponseDTO> criar(@Valid @RequestBody BibliotecaRequest request) {
        Biblioteca biblioteca = bibliotecaService.requestToBiblioteca(request);
        Biblioteca bibliotecaSalva = bibliotecaService.save(biblioteca);
        return new ResponseEntity<>(bibliotecaService.bibliotecaToResponseDTO(bibliotecaSalva, true), HttpStatus.CREATED);
    }

    @Operation(summary = "Lista todas as bibliotecas")
    @GetMapping
    public ResponseEntity<Page<BibliotecaResponseDTO>> listar(@RequestParam(defaultValue = "0") Integer pagina) {
        Pageable pageable = PageRequest.of(pagina, 10, Sort.by("nome").ascending());
        return ResponseEntity.ok(bibliotecaService.findAllDTO(pageable));
    }

    @Operation(summary = "Busca uma biblioteca por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Biblioteca encontrada",
                    content = @Content(schema = @Schema(implementation = BibliotecaResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Biblioteca não encontrada",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping("/{id}")
    public ResponseEntity<BibliotecaResponseDTO> buscarPorId(@PathVariable Long id) {
        Optional<Biblioteca> biblioteca = bibliotecaService.findById(id);
        return biblioteca.map(value -> ResponseEntity.ok(bibliotecaService.bibliotecaToResponseDTO(value, true)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Atualiza uma biblioteca")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Biblioteca atualizada",
                    content = @Content(schema = @Schema(implementation = BibliotecaResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Biblioteca não encontrada",
                    content = @Content(schema = @Schema()))
    })
    @PutMapping("/{id}")
    public ResponseEntity<BibliotecaResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody BibliotecaRequest request) {
        if (!bibliotecaService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        Biblioteca biblioteca = bibliotecaService.requestToBiblioteca(request);
        biblioteca.setId(id);
        Biblioteca bibliotecaAtualizada = bibliotecaService.save(biblioteca);
        return ResponseEntity.ok(bibliotecaService.bibliotecaToResponseDTO(bibliotecaAtualizada, true));
    }

    @Operation(summary = "Remove uma biblioteca")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Biblioteca removida"),
            @ApiResponse(responseCode = "404", description = "Biblioteca não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        if (!bibliotecaService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        bibliotecaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
} 