# LiterAlura

LiterAlura é uma aplicação desenvolvida em Java utilizando Spring Boot, destinada a gerenciar autores e livros. A aplicação permite buscar informações de livros pela API do Gutendex, registrar livros e autores no banco de dados, e listar livros e autores registrados.

## Funcionalidades Principais

1. **Buscar Livro pelo Título**: Permite buscar um livro pelo título utilizando a API do Gutendex e registrar no banco de dados caso não esteja presente.
2. **Listar Livros Registrados**: Exibe todos os livros registrados no banco de dados.
3. **Listar Autores Registrados**: Exibe todos os autores registrados no banco de dados.
4. **Listar Autores Vivos em um Determinado Ano**: Lista os autores que estavam vivos em um ano específico.
5. **Listar Livros em um Determinado Idioma**: Exibe os livros registrados em um idioma específico.
6. **Listar Top 10 Livros mais Baixados**: Exibe os 10 livros mais baixados registrados no banco de dados.

## Tecnologias Utilizadas

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- H2 Database
- Jackson
- Gutendex API

## Estrutura do Projeto

O projeto está organizado nos seguintes pacotes:

- `com.sandro.LiterAlura`: Contém a classe principal `LiterAluraApplication`.
- `com.sandro.LiterAlura.model`: Contém as entidades `Autor` e `Livro` e os registros `DadosAutor`, `DadosLivro` e `PegarDados`.
- `com.sandro.LiterAlura.repository`: Contém os repositórios `AutorRepository` e `LivroRepository`.
- `com.sandro.LiterAlura.service`: Contém as classes `ConsumoAPI`, `ConverteDados` e a interface `IConverteDados`.
- `com.sandro.LiterAlura.principal`: Contém a classe `Principal` que gerencia o menu da aplicação.

## Como Executar o Projeto

### Pré-requisitos

- Java 17 ou superior
- Maven

### Passo a Passo

1. Clone o repositório:
    ```sh
    git clone https://github.com/seu-usuario/LiterAlura.git
    cd LiterAlura
    ```

2. Compile e execute o projeto utilizando Maven:
    ```sh
    mvn clean install
    mvn spring-boot:run
    ```

3. O menu da aplicação será exibido no console. Utilize as opções do menu para interagir com a aplicação.

### Menu de Opções

- 1 - Buscar livro pelo título
- 2 - Listar livros registrados
- 3 - Listar autores registrados
- 4 - Listar autores vivos em um determinado ano
- 5 - Listar livros em um determinado idioma
- 6 - Listar 10 livros mais baixados
- 0 - Sair


