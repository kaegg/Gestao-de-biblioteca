package com.kauan.gestao_de_biblioteca.apiDTO;

import com.kauan.gestao_de_biblioteca.model.Livro;
import com.kauan.gestao_de_biblioteca.model.Usuario;
import com.kauan.gestao_de_biblioteca.model.enums.StatusEmprestimo;

import java.time.LocalDate;

public record EmprestimoDTO(Usuario usuario, Livro livro, LocalDate data_emprestimo, LocalDate data_devolucao, StatusEmprestimo status) {
}
