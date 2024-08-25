package com.kauan.gestao_de_biblioteca.apiDTO;

import java.time.LocalDate;

public record AtualizaLivroDTO(String titulo, String autor, LocalDate data_publicacao, String categoria) {
}
