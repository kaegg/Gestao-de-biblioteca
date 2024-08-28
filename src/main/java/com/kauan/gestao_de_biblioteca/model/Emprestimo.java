package com.kauan.gestao_de_biblioteca.model;

import com.kauan.gestao_de_biblioteca.model.enums.StatusEmprestimo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name="emprestimo")
@Getter
@Setter
public class Emprestimo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "livro_id")
    private Livro livro;

    @NotNull
    private LocalDate data_emprestimo;

    @NotNull
    private LocalDate data_devolucao;

    @Enumerated(EnumType.STRING)
    private StatusEmprestimo status;
}
