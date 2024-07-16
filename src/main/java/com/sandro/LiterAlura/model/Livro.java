package com.sandro.LiterAlura.model;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private List<String> linguagens = new ArrayList<>();
    private Integer quantidadeDownloads;

    @ManyToOne(fetch = FetchType.EAGER)
    private Autor autor;

    public Livro() {

    }

    public Livro(DadosLivro livroRecord) {
        this.titulo = livroRecord.titulo();
        this.linguagens = livroRecord.linguagens();
        this.quantidadeDownloads = livroRecord.quantidadeDownloads();
        this.autor = new Autor(livroRecord.autores().get(0));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<String> getLinguagens() {
        return linguagens;
    }

    public void setLinguagens(List<String> linguagens) {
        this.linguagens = linguagens;
    }

    public Integer getQuantidadeDownloads() {
        return quantidadeDownloads;
    }

    public void setQuantidadeDownloads(Integer quantidadeDownloads) {
        this.quantidadeDownloads = quantidadeDownloads;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "\n Titulo: " + titulo + 
               "\n Linguagens: " + linguagens +
               "\n Quantidade de Downloads: " + quantidadeDownloads + 
               "\n Autor: " + autor.getNome();
    }
}
