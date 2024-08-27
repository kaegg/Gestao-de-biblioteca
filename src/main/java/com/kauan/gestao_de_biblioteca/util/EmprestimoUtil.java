package com.kauan.gestao_de_biblioteca.util;

import com.kauan.gestao_de_biblioteca.model.Emprestimo;
import com.kauan.gestao_de_biblioteca.model.Livro;
import com.kauan.gestao_de_biblioteca.model.Usuario;
import com.kauan.gestao_de_biblioteca.model.enums.StatusEmprestimo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmprestimoUtil {

    public boolean emprestimoAtivoLivro(Livro livro){
        List<Emprestimo> listaEmprestimos = livro.getEmprestimos();

        for (Emprestimo emprestimo : listaEmprestimos) {
            if (emprestimo.getStatus() != StatusEmprestimo.DEVOLVIDO) {
                return true;
            }
        }

        return false;
    }

    public boolean emprestimoAtivoUsuario(Usuario usuario){
        List<Emprestimo> listaEmprestimos = usuario.getEmprestimos();

        for (Emprestimo emprestimo : listaEmprestimos) {
            if (emprestimo.getStatus() != StatusEmprestimo.DEVOLVIDO) {
                return true;
            }
        }

        return false;
    }

}
