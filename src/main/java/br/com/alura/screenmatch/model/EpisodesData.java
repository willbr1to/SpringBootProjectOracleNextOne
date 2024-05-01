package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EpisodesData(@JsonAlias("Episode")Integer numero,
                           @JsonAlias("Title") String titulo,
                           @JsonAlias("Season")String temporada,
                           @JsonAlias("imdbRating")String nota,
                           @JsonAlias("Released") String data) {
}
