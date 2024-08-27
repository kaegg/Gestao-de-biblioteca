package com.kauan.gestao_de_biblioteca.controller;

import com.kauan.gestao_de_biblioteca.apiDTO.AtualizaUsuarioDTO;
import com.kauan.gestao_de_biblioteca.apiDTO.UsuarioDTO;
import com.kauan.gestao_de_biblioteca.model.Livro;
import com.kauan.gestao_de_biblioteca.model.Usuario;
import com.kauan.gestao_de_biblioteca.services.UsuarioServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@Controller
public class UsuarioController {
    @Autowired
    private UsuarioServices usuarioServices;

    @GetMapping
    public List<Usuario> buscarTodos(){
        List<Usuario> usuarios = usuarioServices.buscarTodos();
        return usuarios;
    }

    @GetMapping("/{id}")
    public ResponseEntity <Usuario> buscarUsuario(@PathVariable("id") Long id){
        Usuario usuario = usuarioServices.buscarUsuario(id);
        return ResponseEntity.ok().body(usuario);
    }

    @PostMapping("/post")
    public ResponseEntity cadastrarUsuario(@RequestBody UsuarioDTO usuario){
        ResponseEntity usuarioResposta = usuarioServices.cadastrarUsuario(usuario);

        return ResponseEntity.ok().body(usuarioResposta);
    }

    @PutMapping("/put/{id}")
    public ResponseEntity alterarUsuario(@PathVariable("id") Long id, @RequestBody AtualizaUsuarioDTO atualizaUsuarioDTO){
        ResponseEntity usuarioResposta = usuarioServices.alterarUsuario(id, atualizaUsuarioDTO);

        return ResponseEntity.ok().body(usuarioResposta);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deletarUsuario(@PathVariable("id") Long id){
        ResponseEntity usuarioResposta = usuarioServices.deletarUsuario(id);

        return ResponseEntity.ok().body(usuarioResposta);

    }

    @GetMapping("/livrosRecomendados/{idUsuario}")
    public List<Livro> recomendarLivros(@PathVariable("idUsuario") Long id){
        List<Livro> livros = usuarioServices.recomendarLivros(id);
        return livros;
    }
}
