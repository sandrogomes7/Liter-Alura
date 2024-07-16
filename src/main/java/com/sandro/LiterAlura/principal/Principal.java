package com.sandro.LiterAlura.principal;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import com.sandro.LiterAlura.model.Autor;
import com.sandro.LiterAlura.model.DadosAutor;
import com.sandro.LiterAlura.model.DadosLivro;
import com.sandro.LiterAlura.model.Livro;
import com.sandro.LiterAlura.model.PegarDados;
import com.sandro.LiterAlura.repository.AutorRepository;
import com.sandro.LiterAlura.repository.LivroRepository;
import com.sandro.LiterAlura.service.ConsumoAPI;
import com.sandro.LiterAlura.service.ConverteDados;

public class Principal {

    private Scanner sc = new Scanner(System.in);

    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();
    private LivroRepository livroRepository;
    private AutorRepository autorRepository;

    public Principal(AutorRepository autorRepository, LivroRepository livroRepository) {
        this.autorRepository = autorRepository;
        this.livroRepository = livroRepository;
    }

    public void exibeMenu() {
        var opcao = -1;
        var menu = """
                \n --- Liter Alura ---

                1 - Buscar livro pelo título
                2 - Listar livros registrados
                3 - Listar autores registrados
                4 - Listar autores vivos em um determinado ano
                5 - Listar livros em um determinado idioma
                6 - Listar 10 livros mais baixados
                0 - Sair
                """;
        while (opcao != 0) {

            System.out.println(menu);
            System.out.print("Digite a opção desejada: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivroPorTitulo();
                    break;
                case 2:
                    listarLivrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosEmDetAno();
                    break;
                case 5:
                    listarLivrosPorIdioma();
                    break;
                case 6:
                    listarTop10Livros();
                    break;
                case 0:
                    System.out.println("Encerrando...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }

    }

    private boolean ehNumero(String titulo) {
        try {
            Double.parseDouble(titulo);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void buscarLivroPorTitulo() {
        System.out.print("Digite o título do livro: ");
        var titulo = sc.nextLine();

        if (!titulo.isBlank() && !ehNumero(titulo)) {

            String URL = "https://gutendex.com/books/?search=";
            var endereco = URL + titulo.replace(" ", "%20");
            var json = consumoApi.obterDados(endereco);

            var dados = conversor.obterDados(json, PegarDados.class);
            Optional<DadosLivro> livroBuscado = dados.resultadoConsulta().stream()
                    .filter(l -> l.titulo().equalsIgnoreCase(titulo))
                    .findFirst();

            if (livroBuscado.isPresent()) {
                var livroDados = livroBuscado.get();

                if (verificarLivroPresenteNoBD(livroDados)) {
                    System.out.println("Livro já registrado!");
                } else {
                    var livro = new Livro(livroDados);
                    DadosAutor autorDados = livroDados.autores().get(0);
                    Optional<Autor> autor = autorRepository.buscarByNome(autorDados.nome());

                    if (autor.isPresent()) {
                        Autor autorExistente = autor.get();
                        livro.setAutor(autorExistente);
                        autorExistente.getLivros().add(livro);
                        autorRepository.save(autorExistente);
                    } else {
                        Autor novoAutor = new Autor(autorDados);
                        livro.setAutor(novoAutor);
                        novoAutor.getLivros().add(livro);
                        autorRepository.save(novoAutor);
                    }
                    livroRepository.save(livro);
                    System.out.println("\n" + livro.getTitulo());
                    System.out.println("Livro registrado com sucesso!");
                }
            } else {
                System.out.println("Livro '" + titulo + "' não encontrado!");
            }
        } else {
            System.out.println("Título inválido!");
        }

    }

    private boolean verificarLivroPresenteNoBD(DadosLivro livroDados) {
        Livro livro = new Livro(livroDados);
        return livroRepository.verificaLivroExite(livro.getTitulo());

    }

    private void listarLivrosRegistrados() {
        System.out.println("--- Livros Registrados ---");
        List<Livro> livros = livroRepository.findAll();

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro registrado!");
        } else {
            livros.forEach(System.out::println);
        }
    }

    private void listarAutoresRegistrados() {
        System.out.println("--- Autores Registrados ---");
        List<Autor> autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor registrado!");
        } else {
            autores.forEach(System.out::println);
        }
    }

    private void listarAutoresVivosEmDetAno() {
        System.out.print("Digite o ano: ");
        if (sc.hasNextInt()) {
            var ano = sc.nextInt();

            List<Autor> autores = autorRepository.buscarAutorVivo(ano);

            if (autores.isEmpty()) {
                System.out.println("Nenhum autor vivo registrado!");
            } else {
                autores.forEach(System.out::println);
            }
        } else {
            System.out.println("Ano inválido!");
        }

    }

    private void listarLivrosPorIdioma() {
        var idiomas = """
                \nes - Espanhol
                \nen - Inglês
                \nfr - Francês
                \npt - Português
                """;
        System.out.println(idiomas);
        System.out.print("Digite o idioma: ");
        var idioma = sc.nextLine();

        if (idioma.equalsIgnoreCase("es") || idioma.equalsIgnoreCase("fr") || idioma.equalsIgnoreCase("en")
                || idioma.equalsIgnoreCase("pt")) {
            List<Livro> livros = livroRepository.buscarLivroPorLinguagem(idioma);

            if (livros.isEmpty()) {
                System.out.println("Nenhum livro registrado nesse idioma!");
            } else {
                livros.forEach(System.out::println);
            }

        } else {
            System.out.println("Idioma inválido!");
        }
    }

    private void listarTop10Livros() {
        System.out.println("--- Top 10 Livros ---");
        List<Livro> livros = livroRepository.buscarTop10();

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro registrado!");
        } else {
            livros.forEach(System.out::println);
        }
    }
}
