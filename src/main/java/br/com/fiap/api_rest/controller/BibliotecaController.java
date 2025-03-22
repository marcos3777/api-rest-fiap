package br.com.fiap.api_rest.controller;

import br.com.fiap.api_rest.model.Biblioteca;
import br.com.fiap.api_rest.repository.BibliotecaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bibliotecas")
public class BibliotecaController {

    @Autowired
    private BibliotecaRepository bibliotecaRepository;

    @GetMapping
    public List<Biblioteca> listarTodas() {
        return bibliotecaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Biblioteca> buscarPorId(@PathVariable Long id) {
        Optional<Biblioteca> biblioteca = bibliotecaRepository.findById(id);
        return biblioteca.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Biblioteca> criar(@RequestBody Biblioteca biblioteca) {
        Biblioteca novaBiblioteca = bibliotecaRepository.save(biblioteca);
        return ResponseEntity.ok(novaBiblioteca);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Biblioteca> atualizar(@PathVariable Long id, @RequestBody Biblioteca biblioteca) {
        if (!bibliotecaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        biblioteca.setId(id);
        Biblioteca bibliotecaAtualizada = bibliotecaRepository.save(biblioteca);
        return ResponseEntity.ok(bibliotecaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!bibliotecaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        bibliotecaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
} 