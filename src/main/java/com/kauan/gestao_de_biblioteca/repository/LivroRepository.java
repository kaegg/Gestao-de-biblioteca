package com.kauan.gestao_de_biblioteca.repository;

import com.kauan.gestao_de_biblioteca.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    Optional<Livro> findByIsbn(String isbn);
}
