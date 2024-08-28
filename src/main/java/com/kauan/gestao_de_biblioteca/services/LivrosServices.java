package com.kauan.gestao_de_biblioteca.services;

import com.kauan.gestao_de_biblioteca.apiDTO.AtualizaLivroDTO;
import com.kauan.gestao_de_biblioteca.apiDTO.LivroDTO;
import com.kauan.gestao_de_biblioteca.model.Livro;
import com.kauan.gestao_de_biblioteca.repository.LivroRepository;
import com.kauan.gestao_de_biblioteca.util.EmprestimoUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivrosServices {
    @Autowired
    private LivroRepository livroRepository;

    public List<Livro> buscarTodos() {
        return livroRepository.findAll();
    }

    public Livro buscarLivro(Long id) {
        Optional<Livro> livro = livroRepository.findById(id);
        return livro.orElseThrow(() -> new EntityNotFoundException("Livro não encontrado com o id: " + id));
    }

    public ResponseEntity<Object> cadastrarLivro(LivroDTO livro) {
        Livro novoLivro = new Livro();

        if(livroRepository.findByIsbn(livro.isbn()).isEmpty()){
            novoLivro.setTitulo(livro.titulo());
            novoLivro.setAutor(livro.autor());
            novoLivro.setIsbn(livro.isbn());
            novoLivro.setData_publicacao(livro.data_publicacao());
            novoLivro.setCategoria(livro.categoria());

            livroRepository.save(novoLivro);

            return ResponseEntity.ok(novoLivro);
        }else{
            return ResponseEntity.badRequest().body("ISBN já cadastrado");
        }
    }

    public ResponseEntity<Object> alterarLivro(Long id, AtualizaLivroDTO atualizaLivroDTO){
        Optional<Livro> livro = livroRepository.findById(id);

        if(livro.isPresent()){
            if(atualizaLivroDTO.titulo() != null){
                livro.get().setTitulo(atualizaLivroDTO.titulo());
            }

            if(atualizaLivroDTO.autor() != null){
                livro.get().setAutor(atualizaLivroDTO.autor());
            }

            if(atualizaLivroDTO.data_publicacao() != null){
                livro.get().setData_publicacao(atualizaLivroDTO.data_publicacao());
            }

            if(atualizaLivroDTO.categoria() != null){
                livro.get().setCategoria(atualizaLivroDTO.categoria());
            }

            livroRepository.save(livro.get());

            return ResponseEntity.ok(livro.get());
        }else{
            return ResponseEntity.badRequest().body("Livro com id: " + id + " não encontrado para realizar alteração");
        }
    }

    public ResponseEntity<Object> deletarLivro(Long id){
        EmprestimoUtil emprestimoUtil = new EmprestimoUtil();
        Optional<Livro> livro = livroRepository.findById(id);

        if(emprestimoUtil.emprestimoAtivoLivro(livro.get())){
            return ResponseEntity.badRequest().body("Não é possível realizar a remoção deste livro, pois há um empréstimo ativo para ele");
        }

        if(livro.isPresent()){
            livroRepository.deleteById(id);

            return ResponseEntity.ok().body("Livro com Id: " + id + " deletado com sucesso!");
        }else{
            return ResponseEntity.badRequest().body("Livro com id: " + id + " não encontrado para realizar remoção");
        }
    }
}
