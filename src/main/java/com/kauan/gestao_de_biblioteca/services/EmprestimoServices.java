package com.kauan.gestao_de_biblioteca.services;

import com.kauan.gestao_de_biblioteca.apiDTO.AtualizaEmprestimoDTO;
import com.kauan.gestao_de_biblioteca.apiDTO.EmprestimoDTO;
import com.kauan.gestao_de_biblioteca.model.Emprestimo;
import com.kauan.gestao_de_biblioteca.model.Livro;
import com.kauan.gestao_de_biblioteca.model.Usuario;
import com.kauan.gestao_de_biblioteca.model.enums.StatusEmprestimo;
import com.kauan.gestao_de_biblioteca.repository.EmprestimoRepository;
import com.kauan.gestao_de_biblioteca.repository.LivroRepository;
import com.kauan.gestao_de_biblioteca.repository.UsuarioRepository;
import com.kauan.gestao_de_biblioteca.util.EmprestimoUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EmprestimoServices {
    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LivroRepository livroRepository;

    public List<Emprestimo> buscarTodos() {
        return emprestimoRepository.findAll();
    }

    public Emprestimo buscarEmprestimo(Long id) {
        Optional<Emprestimo> emprestimo = emprestimoRepository.findById(id);
        return emprestimo.orElseThrow(() -> new EntityNotFoundException("Empréstimo não encontrado com o id: " + id));
    }

    public ResponseEntity<Object> cadastrarEmprestimo(EmprestimoDTO emprestimo) {
        Emprestimo novoEmprestimo = new Emprestimo();
        EmprestimoUtil emprestimoUtil = new EmprestimoUtil();
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(emprestimo.usuario().getId());
        Optional<Livro> livroOptional = livroRepository.findById(emprestimo.livro().getId());

        if(usuarioOptional.isEmpty()){
            return ResponseEntity.badRequest().body("Usuário não encontrado para realizar empréstimo");
        }

        if(livroOptional.isEmpty()){
            return ResponseEntity.badRequest().body("Livro não encontrado para realizar empréstimo");
        }

        if(emprestimo.data_emprestimo().isAfter(LocalDate.now())){
            return ResponseEntity.badRequest().body("A data de empréstimo não pode ser maior que a data atual");
        }

        if(emprestimo.data_devolucao().isBefore(emprestimo.data_emprestimo())){
            return ResponseEntity.badRequest().body("A data de devolução não pode ser menor que a data de emprestimo");
        }

        if(emprestimoUtil.emprestimoAtivoLivro(livroOptional.get())){
            return ResponseEntity.badRequest().body("Já existe um empréstimo ativo para este livro");
        }

        novoEmprestimo.setUsuario(usuarioOptional.get());
        novoEmprestimo.setLivro(livroOptional.get());
        novoEmprestimo.setData_emprestimo(emprestimo.data_emprestimo());
        novoEmprestimo.setData_devolucao(emprestimo.data_devolucao());
        novoEmprestimo.setStatus(emprestimo.status());

        emprestimoRepository.save(novoEmprestimo);

        return ResponseEntity.ok(novoEmprestimo);
    }

    public ResponseEntity<Object> atualizarEmprestimo(Long id, AtualizaEmprestimoDTO atualizaEmprestimoDTO){
        Optional<Emprestimo> emprestimo = emprestimoRepository.findById(id);

        if(emprestimo.isPresent() && emprestimo.get().getStatus() != StatusEmprestimo.DEVOLVIDO){
            if(atualizaEmprestimoDTO.data_devolucao() != null){
                emprestimo.get().setData_devolucao(atualizaEmprestimoDTO.data_devolucao());
            }

            if(atualizaEmprestimoDTO.status() != null){
                emprestimo.get().setStatus(atualizaEmprestimoDTO.status());
            }

            emprestimoRepository.save(emprestimo.get());

            return ResponseEntity.ok(emprestimo.get());
        }else{
            return ResponseEntity.badRequest().body("Status do empréstimo é Devolvido ou empréstimo inexistente");
        }
    }
}
