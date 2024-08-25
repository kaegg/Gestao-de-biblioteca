package com.kauan.gestao_de_biblioteca.services;

import com.kauan.gestao_de_biblioteca.apiDTO.AtualizaLivroDTO;
import com.kauan.gestao_de_biblioteca.model.Livro;
import com.kauan.gestao_de_biblioteca.repository.LivroRepository;
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

    public ResponseEntity<Livro> cadastrarLivro(Livro livro) {
        Livro novoLivro = new Livro();

        if(livroRepository.findByIsbn(livro.getIsbn()).isEmpty()){
            novoLivro.setTitulo(livro.getTitulo());
            novoLivro.setAutor(livro.getAutor());
            novoLivro.setIsbn(livro.getIsbn());
            novoLivro.setData_publicacao(livro.getData_publicacao());
            novoLivro.setCategoria(livro.getCategoria());

            livroRepository.save(novoLivro);

            return ResponseEntity.ok(novoLivro);
        }

        return ResponseEntity.badRequest().build();
    }

    public void alterarLivro(Long id, AtualizaLivroDTO atualizaLivroDTO){
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
        }else{
            throw new EntityNotFoundException("Livro com id: " + id + " não encontrado para realizar alteração");
        }
    }

    public void deletarLivro(Long id){
        Optional<Livro> livro = livroRepository.findById(id);

        if(livro.isPresent()){
            livroRepository.deleteById(id);
        }else{
            throw new EntityNotFoundException("Livro com id: " + id + " não encontrado para realizar remoção");
        }
    }
}
