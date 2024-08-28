# Gestão de Biblioteca

Este é um projeto de gestão de biblioteca desenvolvido com Spring Boot, utilizando um banco de dados MySQL. A aplicação permite realizar o gerenciamento de usuários, livros e empréstimos.

## Pré-requisitos

- **Java 21** ou superior
- **Maven 3.6.0** ou superior
- **MySQL 8.0** ou superior

## Configuração do Banco de Dados

### 1. Criação do Banco de Dados

   Execute os seguintes comandos SQL para criar o banco de dados e as tabelas necessárias:

   ```sql
   CREATE DATABASE gestao_de_biblioteca;

   USE gestao_de_biblioteca;

   CREATE TABLE usuario (
       id BIGINT AUTO_INCREMENT PRIMARY KEY,
       nome VARCHAR(255) NOT NULL,
       email VARCHAR(255) NOT NULL,
       data_cadastro TIMESTAMP NOT NULL,
       telefone VARCHAR(20) NOT NULL
   );

   CREATE TABLE livro (
       id BIGINT AUTO_INCREMENT PRIMARY KEY,
       titulo VARCHAR(255) NOT NULL,
       autor VARCHAR(255) NOT NULL,
       isbn VARCHAR(20) NOT NULL,
       data_publicacao DATE NOT NULL,
       categoria VARCHAR(100) NOT NULL
   );

   CREATE TABLE emprestimo (
       id BIGINT AUTO_INCREMENT PRIMARY KEY,
       usuario_id BIGINT NOT NULL,
       livro_id BIGINT NOT NULL,
       data_emprestimo DATE NOT NULL,
       data_devolucao DATE NOT NULL,
       status ENUM('Emprestado', 'Devolvido', 'Atrasado') NOT NULL,
       FOREIGN KEY (usuario_id) REFERENCES usuario(id),
       FOREIGN KEY (livro_id) REFERENCES livro(id)
   );
   ```

### 2. Configuração do application.properties
   ```properties
   spring.application.name=gestao_de_biblioteca

   spring.datasource.url=jdbc:mysql://localhost:3306/gestao_de_biblioteca
   spring.datasource.username=root
   spring.datasource.password=root
   ```

## Endpoints

### Usuário

#### 1. Buscar Todos os Usuários
**Método:** `GET`  
**Endpoint:** `/usuarios`  
**Descrição:** Retorna uma lista de todos os usuários.

#### 2. Buscar Usuário por ID
**Método:** `GET`  
**Endpoint:** `/usuarios/{id}`  
**Descrição:** Retorna o usuário com o id especificado.

#### 3. Cadastrar Novo Usuário
**Método:** `POST`  
**Endpoint:** `/usuarios/post`  
**Descrição:** Realiza o cadastro de um novo usuário, sendo necessário passar os campos nome, email e telefone respectivamente.

**Exemplo:**
```json
{
    "nome": "Example",
    "email": "example@example.com",
    "telefone": "(44) 99999-9999"
}
```

#### 4. Atualizar Usuário
**Método:** `PUT`  
**Endpoint:** `/usuarios/put/{id}`  
**Descrição:** Realiza a atualização do usuário através de seu Id, sendo necessário passar o campo nome ou email.

**Exemplo:**
```json
{
    "nome": "Example",
    "telefone": "(44) 99999-9999"
}
```

#### 5. Deletar Usuário
**Método:** `DELETE`  
**Endpoint:** `/usuarios/delete/{id}`  
**Descrição:** Realiza a remoção do usuário através de seu Id, caso haja empréstimos ativos para ele a remoção não poderá ser efetuada.

#### 6. Buscar Livros Recomendados Para o Usuário
**Método:** `GET`  
**Endpoint:** `/usuarios/livrosRecomendados/{idUsuario}`  
**Descrição:** Retorna uma lista de livros recomendados para o usuário com base na categoria de livros mais emprestadas por ele.

## Livro

#### 1. Buscar Todos os Livros
**Método:** `GET`  
**Endpoint:** `/livros`  
**Descrição:** Retorna uma lista de todos os livros.

#### 2. Buscar Livros por ID
**Método:** `GET`  
**Endpoint:** `/livros/{id}`  
**Descrição:** Retorna o livro com o id especificado.

#### 3. Cadastrar Novo Livro
**Método:** `POST`  
**Endpoint:** `/livros/post`  
**Descrição:** Realiza o cadastro de um novo livro, sendo necessário passar os campos titulo, autor, isbn, data_publicacao e categoria respectivamente.

**Exemplo:**
```json
{
  
  "titulo": "Exemplo titulo",
  "autor": "Exemplo autor",
  "isbn": "99-9999999",
  "data_publicacao": "2024-08-28",
  "categoria": "Exemplo categoria"

}
```

#### 4. Atualizar Livro
**Método:** `PUT`  
**Endpoint:** `/livros/put/{id}`  
**Descrição:** Realiza a atualização do livro através de seu Id, sendo necessário passar o campo titulo, autor, data_publicacao ou categoria.

**Exemplo:**
```json
{
    "titulo": "Exemplo titulo",
    "autor": "Exemplo autor",
    "data_publicacao": "2024-08-28",
    "categoria": "Exemplo categoria"
}
```

#### 5. Deletar Livro
**Método:** `DELETE`  
**Endpoint:** `/livros/delete/{id}`  
**Descrição:** Realiza a remoção do Livro através de seu Id, caso ele esteja emprestado a remoção não poderá ser efetuada.

## Empréstimo

#### 1. Buscar Todos os Empréstimos
**Método:** `GET`  
**Endpoint:** `/emprestimos`  
**Descrição:** Retorna uma lista de todos os empréstimos.

#### 2. Buscar Empréstimos por ID
**Método:** `GET`  
**Endpoint:** `/emprestimos/{id}`  
**Descrição:** Retorna o empréstimo com o id especificado.

#### 3. Cadastrar Novo Empréstimo
**Método:** `POST`  
**Endpoint:** `/emprestimos/post`  
**Descrição:** Realiza o cadastro de um novo empréstimo, sendo necessário passar os campos id_usuario, id_livro, data_emprestimo(não pode ser maior que a data atual), data_devolucao(não pode ser menor que a data de empréstimo) e status("EMPRESTADO", "DEVOLVIDO" ou "ATRASADO") respectivamente.

**Exemplo:**
```json
{
  "usuario": {
      "id": 1
  },
  "livro": {
      "id": 1
  },
  "data_emprestimo": "2024-08-28",
  "data_devolucao": "2024-09-07",
  "status": "EMPRESTADO"
}
```

#### 4. Atualizar Empréstimo
**Método:** `PUT`  
**Endpoint:** `/emprestimos/put/{id}`  
**Descrição:** Realiza a atualização do empréstimo através de seu Id, sendo necessário passar o campo data_devolucao ou status("EMPRESTADO", "DEVOLVIDO" ou "ATRASADO"). Caso o status do empréstimo seja "DEVOLVIDO" não será possível realizar a atualização.

**Exemplo:**
```json
{
    "data_devolucao": "2024-09-07",
    "status": "EMPRESTADO"
}
```
