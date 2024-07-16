package com.sandro.LiterAlura.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.sandro.LiterAlura.model.Autor;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    @Query(value = "SELECT * FROM autores a WHERE a.nome LIKE %:nome%;", nativeQuery = true)
    Optional<Autor> buscarByNome(String nome);

    @Query(value = "SELECT * FROM autores a WHERE :ano BETWEEN CAST(a.data_nascimento AS integer) AND CAST(a.data_falecimento AS integer);", nativeQuery = true)
    List<Autor> buscarAutorVivo(Integer ano);
}
