package br.com.fiap.api_rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.api_rest.model.Autor;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
} 