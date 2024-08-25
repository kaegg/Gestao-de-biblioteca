package com.kauan.gestao_de_biblioteca.services;

import com.kauan.gestao_de_biblioteca.apiDTO.AtualizaUsuarioDTO;
import com.kauan.gestao_de_biblioteca.apiDTO.UsuarioDTO;
import com.kauan.gestao_de_biblioteca.model.Usuario;
import com.kauan.gestao_de_biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.apache.commons.validator.routines.EmailValidator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServices {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarUsuario(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public ResponseEntity<Usuario> cadastrarUsuario(UsuarioDTO usuario) {
        Usuario novoUsuario = new Usuario();
        boolean emailValido = EmailValidator.getInstance().isValid(usuario.email());

        if(!emailValido){
            return ResponseEntity.badRequest().build();
        }

        if(usuarioRepository.findByEmail(usuario.email()).isEmpty()){
            novoUsuario.setNome(usuario.nome());
            novoUsuario.setEmail(usuario.email());
            novoUsuario.setData_cadastro(LocalDateTime.now());
            novoUsuario.setTelefone(usuario.telefone());

            usuarioRepository.save(novoUsuario);

            return ResponseEntity.ok(novoUsuario);
        }

        return ResponseEntity.badRequest().build();
    }

    public void alterarUsuario(Long id, AtualizaUsuarioDTO atualizaUsuarioDTO){
            Optional<Usuario> usuario = usuarioRepository.findById(id);

            if(usuario.isPresent()){
                if(atualizaUsuarioDTO.nome() != null){
                    usuario.get().setNome(atualizaUsuarioDTO.nome());
                }

                if(atualizaUsuarioDTO.telefone() != null){
                    usuario.get().setTelefone(atualizaUsuarioDTO.telefone());
                }

                usuarioRepository.save(usuario.get());
            }
    }

    public void deletarUsuario(Long id){
        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if(usuario.isPresent()){
            usuarioRepository.deleteById(id);
        }
    }
}
