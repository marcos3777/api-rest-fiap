package br.com.fiap.api_rest.controller;

import br.com.fiap.api_rest.dto.AutorRequest;
import br.com.fiap.api_rest.dto.AutorResponseDTO;
import br.com.fiap.api_rest.model.Autor;
import br.com.fiap.api_rest.service.AutorService;
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
@RequestMapping("/autores")
@Tag(name = "api-autores")
public class AutorController {

    @Autowired
    private AutorService autorService;

    @Operation(summary = "Cria um novo autor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Autor criado com sucesso",
                    content = @Content(schema = @Schema(implementation = AutorResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema()))
    })
    @PostMapping
    public ResponseEntity<AutorResponseDTO> criar(@Valid @RequestBody AutorRequest request) {
        Autor autor = autorService.requestToAutor(request);
        Autor autorSalvo = autorService.save(autor);
        return new ResponseEntity<>(autorService.autorToResponseDTO(autorSalvo, true), HttpStatus.CREATED);
    }

    @Operation(summary = "Lista todos os autores")
    @GetMapping
    public ResponseEntity<Page<AutorResponseDTO>> listar(@RequestParam(defaultValue = "0") Integer pagina) {
        Pageable pageable = PageRequest.of(pagina, 10, Sort.by("nome").ascending());
        return ResponseEntity.ok(autorService.findAllDTO(pageable));
    }

    @Operation(summary = "Busca um autor por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autor encontrado",
                    content = @Content(schema = @Schema(implementation = AutorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping("/{id}")
    public ResponseEntity<AutorResponseDTO> buscarPorId(@PathVariable Long id) {
        Optional<Autor> autor = autorService.findById(id);
        return autor.map(value -> ResponseEntity.ok(autorService.autorToResponseDTO(value, true)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Atualiza um autor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autor atualizado",
                    content = @Content(schema = @Schema(implementation = AutorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado",
                    content = @Content(schema = @Schema()))
    })
    @PutMapping("/{id}")
    public ResponseEntity<AutorResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody AutorRequest request) {
        if (!autorService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        Autor autor = autorService.requestToAutor(request);
        autor.setId(id);
        Autor autorAtualizado = autorService.save(autor);
        return ResponseEntity.ok(autorService.autorToResponseDTO(autorAtualizado, true));
    }

    @Operation(summary = "Remove um autor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Autor removido"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        if (!autorService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        autorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
} 