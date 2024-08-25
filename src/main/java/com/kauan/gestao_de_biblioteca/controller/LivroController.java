package com.kauan.gestao_de_biblioteca.controller;

import com.kauan.gestao_de_biblioteca.apiDTO.AtualizaLivroDTO;
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

    @PostMapping("/post")
    public ResponseEntity cadastrarLivro(@RequestBody Livro livro){
        ResponseEntity livroResposta = livrosServices.cadastrarLivro(livro);

        return ResponseEntity.ok().body(livroResposta);
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<Void> alterarLivro(@PathVariable("id") Long id, @RequestBody AtualizaLivroDTO atualizaLivroDTO){
        livrosServices.alterarLivro(id, atualizaLivroDTO);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletarLivro(@PathVariable("id") Long id){
        livrosServices.deletarLivro(id);

        return ResponseEntity.noContent().build();
    }
}
