package com.kauan.gestao_de_biblioteca.controller;

import com.kauan.gestao_de_biblioteca.apiDTO.AtualizaEmprestimoDTO;
import com.kauan.gestao_de_biblioteca.apiDTO.EmprestimoDTO;
import com.kauan.gestao_de_biblioteca.model.Emprestimo;
import com.kauan.gestao_de_biblioteca.services.EmprestimoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emprestimos")
@Controller
public class EmprestimoController {
    @Autowired
    private EmprestimoServices emprestimoServices;

    @GetMapping
    public List<Emprestimo> buscarTodos() {
        return emprestimoServices.buscarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Emprestimo> buscarEmprestimo(@PathVariable("id") Long id){
        Emprestimo emprestimo = emprestimoServices.buscarEmprestimo(id);

        return ResponseEntity.ok().body(emprestimo);
    }

    @PostMapping
    public ResponseEntity cadastrarEmprestimo(@RequestBody EmprestimoDTO emprestimo){
        ResponseEntity<Object> emprestimoResposta = emprestimoServices.cadastrarEmprestimo(emprestimo);

        return ResponseEntity.ok().body(emprestimoResposta);
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizarEmprestimo(@PathVariable("id") Long id, @RequestBody AtualizaEmprestimoDTO atualizaEmprestimoDTO){
        ResponseEntity<Object> emprestimoResposta = emprestimoServices.atualizarEmprestimo(id, atualizaEmprestimoDTO);

        return ResponseEntity.ok().body(emprestimoResposta);
    }
}
