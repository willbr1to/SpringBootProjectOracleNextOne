package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SeriesData(
        @JsonAlias("Title") String titulo,
        @JsonAlias("Genre") String genero,
        @JsonAlias("Year") String ano,
        @JsonAlias("imdbRating") String notaIMDB,
        @JsonAlias("totalSeasons") Integer temporadas){
}
