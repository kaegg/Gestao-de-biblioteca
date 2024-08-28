package com.kauan.gestao_de_biblioteca.repository;

import com.kauan.gestao_de_biblioteca.apiDTO.EmprestimoDTO;
import com.kauan.gestao_de_biblioteca.apiDTO.UsuarioDTO;
import com.kauan.gestao_de_biblioteca.model.Livro;
import com.kauan.gestao_de_biblioteca.model.Usuario;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.assertj.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class LivroRepositoryTest {
    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Deve retornar uma lista de livros sem aqueles que o usuario ja emprestou")
    void findLivrosByCategoriaSemEmprestados() {
        UsuarioDTO usuarioDTO = new UsuarioDTO("Kauan", "teste@teste.com", "44 99999-9999");

        Livro livro1 = new Livro("Livro de Fantasia 1", "Fantasia");
        Livro livro2 = new Livro("Livro de Fantasia 2", "Fantasia");
        Livro livro3 = new Livro("Livro de Ficção 1", "Ficção Científica");

        EmprestimoDTO emprestimo1 = new EmprestimoDTO(usuarioDTO, livro1);
        EmprestimoDTO emprestimo2 = new EmprestimoDTO(usuarioDTO, livro2);

        // Configuração de lista de empréstimos para o usuário
        usuarioDTO.setEmprestimos(Arrays.asList(emprestimo1, emprestimo2));

        // Simulação da chamada ao método findLivrosByCategoriaSemEmprestados
        // (Aqui assumimos que há um método similar em sua classe de serviço que você está testando)
        List<Livro> livrosRecomendados = this.findLivrosByCategoriaSemEmprestados("Fantasia", 1L);

        // Verificações
        assertThat(livrosRecomendados).isNotNull();
        assertThat(livrosRecomendados.size()).isEqualTo(1); // Assumindo que há um livro na categoria "Fantasia" não emprestado
        assertThat(livrosRecomendados.get(0).getTitulo()).isEqualTo("Livro de Fantasia 2"); // Verifica se o livro correto foi retornado
    }
}

    private Livro createLivro(Livro livro){
        Livro novoLivro = new Livro(livro);
        this.entityManager.persist(novoLivro);
        return novoLivro;
    }

    private Usuario createUsuario(UsuarioDTO usuarioDTO){
        Usuario novoUsuario = new Usuario(usuarioDTO);
        this.entityManager.persist(novoUsuario);
        return novoUsuario;
    }
}