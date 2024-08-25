package com.kauan.gestao_de_biblioteca.controller;

import com.kauan.gestao_de_biblioteca.apiDTO.AtualizaUsuarioDTO;
import com.kauan.gestao_de_biblioteca.apiDTO.UsuarioDTO;
import com.kauan.gestao_de_biblioteca.model.Usuario;
import com.kauan.gestao_de_biblioteca.services.UsuarioServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
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
        ResponseEntity usuarioResposta = this.usuarioServices.cadastrarUsuario(usuario);

        return ResponseEntity.ok().body(usuarioResposta);
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<Void> alterarUsuario(@PathVariable("id") Long id, @RequestBody AtualizaUsuarioDTO atualizaUsuarioDTO){
        usuarioServices.alterarUsuario(id, atualizaUsuarioDTO);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable("id") Long id){
        usuarioServices.deletarUsuario(id);

        return ResponseEntity.noContent().build();
    }
}
