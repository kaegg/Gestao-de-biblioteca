# Gestão de Biblioteca

Este é um projeto de gestão de biblioteca desenvolvido com Spring Boot, utilizando um banco de dados MySQL. A aplicação permite realizar o gerenciamento de usuários, livros e empréstimos.

## Pré-requisitos

- **Java 21** ou superior
- **Maven 3.6.0** ou superior
- **MySQL 8.0** ou superior

## Configuração do Banco de Dados

### 1. Criação do Banco de Dados

   A API foi testada utilizando o banco de dados MySQL. No entanto, para facilitar a configuração inicial, o projeto está configurado para utilizar o banco de dados em memória HSQLDB. Isso permite que você execute a aplicação sem a necessidade de configurar um banco de dados externo inicialmente.

Se preferir utilizar o MySQL, basta seguir os passos abaixo:

#### 1.1. Criação do banco de dados

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

#### 1.2. Configuração do application.properties
   ```properties
   spring.application.name=gestao_de_biblioteca

   spring.datasource.url=jdbc:mysql://localhost:3306/gestao_de_biblioteca 

   #spring.datasource.url=jdbc:hsqldb:file:./database/
   spring.datasource.username=root
   spring.datasource.password=root
   spring.jpa.show-sql = true
   spring.jpa.hibernate.ddl-auto = create
   ```

## Endpoints

Para a documentação dos endpoints da API foi utilizado o Swagger UI, você pode acessá-la facilmente através da seguinte URL: http://localhost:8080/swagger-ui/index.html

