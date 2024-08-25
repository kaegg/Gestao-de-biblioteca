package com.kauan.gestao_de_biblioteca.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="livro")
@Getter
@Setter
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String titulo;
    private String autor;
    private String isbn;
    private LocalDate data_publicacao;
    private String categoria;

    @OneToMany(mappedBy = "livro")
    private List<Emprestimo> emprestimos = new ArrayList<>();
}
