package com.sandro.LiterAlura.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PegarDados(@JsonAlias("results") List<DadosLivro> resultadoConsulta) {
}
