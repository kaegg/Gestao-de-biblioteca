package com.kauan.gestao_de_biblioteca.repository;

import com.kauan.gestao_de_biblioteca.model.Livro;
import com.kauan.gestao_de_biblioteca.model.enums.StatusEmprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    Optional<Livro> findByIsbn(String isbn);

    @Query("SELECT l FROM Livro l WHERE l.categoria IN :categorias AND l.id NOT IN :ids")
    List<Livro> findByCategoriaSemId(List<String> categoria, List<Long> ids);
}
