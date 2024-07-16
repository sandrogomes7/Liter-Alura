package com.sandro.LiterAlura.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.sandro.LiterAlura.model.Livro;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    @Query(value = "SELECT COUNT(l) > 0 FROM livros l WHERE l.titulo LIKE %:titulo%;", nativeQuery = true)
    boolean verificaLivroExite(String titulo);

    @Query(value = "SELECT l.* FROM livros l WHERE :linguagem = ANY (l.linguagens);", nativeQuery = true)
    List<Livro> buscarLivroPorLinguagem(String linguagem);

    @Query(value = "SELECT * FROM livros l ORDER BY l.quantidade_downloads DESC LIMIT 10;", nativeQuery = true)
    List<Livro> buscarTop10();
}
