package com.kauan.gestao_de_biblioteca.controller;

import com.kauan.gestao_de_biblioteca.apiDTO.AtualizaLivroDTO;
import com.kauan.gestao_de_biblioteca.apiDTO.LivroDTO;
import com.kauan.gestao_de_biblioteca.model.Livro;
import com.kauan.gestao_de_biblioteca.services.LivrosServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livros")
@Controller
public class LivroController {
    @Autowired
    private LivrosServices livrosServices;

    @GetMapping
    public List<Livro> buscarTodos() {
        return livrosServices.buscarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity <Livro> buscarLivro(@PathVariable("id") Long id){
        Livro livro = livrosServices.buscarLivro(id);
        return ResponseEntity.ok().body(livro);
    }

    @PostMapping
    public ResponseEntity cadastrarLivro(@RequestBody LivroDTO livro){
        ResponseEntity livroResposta = livrosServices.cadastrarLivro(livro);

        return ResponseEntity.ok().body(livroResposta);
    }

    @PutMapping("/{id}")
    public ResponseEntity alterarLivro(@PathVariable("id") Long id, @RequestBody AtualizaLivroDTO atualizaLivroDTO){
        ResponseEntity livroResposta = livrosServices.alterarLivro(id, atualizaLivroDTO);

        return ResponseEntity.ok().body(livroResposta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletarLivro(@PathVariable("id") Long id){
        ResponseEntity livroResposta = livrosServices.deletarLivro(id);

        return ResponseEntity.ok().body(livroResposta);
    }
}
