package com.kauan.gestao_de_biblioteca.services;

import com.kauan.gestao_de_biblioteca.apiDTO.AtualizaUsuarioDTO;
import com.kauan.gestao_de_biblioteca.apiDTO.UsuarioDTO;
import com.kauan.gestao_de_biblioteca.model.Emprestimo;
import com.kauan.gestao_de_biblioteca.model.Livro;
import com.kauan.gestao_de_biblioteca.model.Usuario;
import com.kauan.gestao_de_biblioteca.repository.EmprestimoRepository;
import com.kauan.gestao_de_biblioteca.repository.LivroRepository;
import com.kauan.gestao_de_biblioteca.repository.UsuarioRepository;
import com.kauan.gestao_de_biblioteca.util.EmprestimoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.apache.commons.validator.routines.EmailValidator;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UsuarioServices {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private LivroRepository livroRepository;

    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarUsuario(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.orElseThrow(() -> new RuntimeException("Usuário não encontrado com o id: " + id));
    }

    public ResponseEntity<Object> cadastrarUsuario(UsuarioDTO usuario) {
        Usuario novoUsuario = new Usuario();
        boolean emailValido = EmailValidator.getInstance().isValid(usuario.email());

        if(!emailValido){
            return ResponseEntity.badRequest().body("Email inválido, ele deve seguir o padrão example@example.com");
        }

        if(usuarioRepository.findByEmail(usuario.email()).isEmpty()){
            novoUsuario.setNome(usuario.nome());
            novoUsuario.setEmail(usuario.email());
            novoUsuario.setData_cadastro(LocalDateTime.now());
            novoUsuario.setTelefone(usuario.telefone());

            usuarioRepository.save(novoUsuario);

            return ResponseEntity.ok(novoUsuario);
        }else{
            return ResponseEntity.badRequest().body("Este email já esta sendo utilizado");
        }
    }

    public ResponseEntity<Object> alterarUsuario(Long id, AtualizaUsuarioDTO atualizaUsuarioDTO){
            Optional<Usuario> usuario = usuarioRepository.findById(id);

            if(usuario.isPresent()){
                if(atualizaUsuarioDTO.nome() != null){
                    usuario.get().setNome(atualizaUsuarioDTO.nome());
                }

                if(atualizaUsuarioDTO.telefone() != null){
                    usuario.get().setTelefone(atualizaUsuarioDTO.telefone());
                }

                usuarioRepository.save(usuario.get());

                return ResponseEntity.ok(usuario.get());
            }else{
                return ResponseEntity.badRequest().body("Usuário com id: " + id + " não encontrado para realizar alteração");
            }
    }

    public ResponseEntity<Object> deletarUsuario(Long id){
        EmprestimoUtil emprestimoUtil = new EmprestimoUtil();
        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if(emprestimoUtil.emprestimoAtivoUsuario(usuario.get())){
            return ResponseEntity.badRequest().body("Não é possível realizar a remoção do usuario, pois há um empréstimo ativo para ele");
        }

        if(usuario.isPresent()){
            usuarioRepository.deleteById(id);

            return ResponseEntity.ok().body("Usuário de Id: " + id + " deletado com sucesso!");
        }else{
            return ResponseEntity.badRequest().body("Usuário com id: " + id + " não encontrado para realizar remoção");
        }
    }

    public List<Livro> recomendarLivros(Long id){
        Map<String, Integer> contador = new HashMap<>();
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);

        if(usuarioOpt.isPresent()){
            Usuario usuario = usuarioOpt.get();

            for (Emprestimo emprestimo : usuario.getEmprestimos()) {
                String categoria = emprestimo.getLivro().getCategoria();
                contador.put(categoria, contador.getOrDefault(categoria, 0) + 1);
            }

            String categoriaMaisEmprestada = "";
            int maxContagem = 0;

            for (Map.Entry<String, Integer> entry : contador.entrySet()) {
                if (entry.getValue() > maxContagem) {
                    categoriaMaisEmprestada = entry.getKey();
                    maxContagem = entry.getValue();
                }
            }

            List<Livro> livrosRecomendados = livroRepository.findLivrosByCategoriaSemEmprestados(categoriaMaisEmprestada, id);

            return livrosRecomendados;
        }

        return List.of();
    }
}
