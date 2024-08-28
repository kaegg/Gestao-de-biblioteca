package com.kauan.gestao_de_biblioteca.apiDTO;

import java.time.LocalDate;

public record LivroDTO(String titulo, String autor, String isbn, LocalDate data_publicacao, String categoria) {
}
