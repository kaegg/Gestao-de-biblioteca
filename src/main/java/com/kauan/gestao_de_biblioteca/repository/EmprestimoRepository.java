package com.kauan.gestao_de_biblioteca.repository;

import com.kauan.gestao_de_biblioteca.model.Emprestimo;
import com.kauan.gestao_de_biblioteca.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
    List<Emprestimo> findByUsuario(Usuario usuario);
}