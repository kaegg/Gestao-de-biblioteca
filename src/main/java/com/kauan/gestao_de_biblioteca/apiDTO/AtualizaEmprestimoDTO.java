package com.kauan.gestao_de_biblioteca.apiDTO;

import com.kauan.gestao_de_biblioteca.model.enums.StatusEmprestimo;

import java.time.LocalDate;

public record AtualizaEmprestimoDTO(LocalDate data_devolucao, StatusEmprestimo status) {
}
