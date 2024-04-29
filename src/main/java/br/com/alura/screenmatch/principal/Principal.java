package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.SeasonsData;
import br.com.alura.screenmatch.model.SeriesData;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.Deserializador;

import java.util.ArrayList;
import java.util.Scanner;


public class Principal {

    private final String API_KEY = "apikey=7e34390d";
    private final String ENDERECO = "http://www.omdbapi.com/?";
    private ConsumoApi consumoApi = new ConsumoApi();
    private Scanner userInputs = new Scanner(System.in);
    private Deserializador deserializador = new Deserializador();

    public void printMenu (){
        System.out.println("Digite o nome da serie: ");
        var serie = userInputs.nextLine();
        var json = consumoApi.obterDados(ENDERECO+API_KEY+"&t="+serie.replace(" ", "+"));
        SeriesData serieData = deserializador.deserializa(json, SeriesData.class);
        System.out.println(serieData);

        ArrayList<SeasonsData> temporadas = new ArrayList<>();
        for(int i = 1; i <= serieData.temporadas(); i++) {
            var selectTemporada = "&season=" + i;
            json = consumoApi.obterDados(ENDERECO+API_KEY+"&t="+serie.replace(" ", "+") + selectTemporada);
            SeasonsData seasonData = deserializador.deserializa(json, SeasonsData.class);
            temporadas.add(seasonData);
        }
        temporadas.forEach(System.out::println);
//        for( int i = 0; i < serieData.temporadas(); i++) {
//            List<EpisodesData> seasonEps = temporadas.get(i).episodios();
//            System.out.println();
//            System.out.println("Número da temporada: " + temporadas.get(i).temporada());
//            for( int j = 0; j < seasonEps.size(); j++ ){
//                System.out.println(seasonEps.get(j).titulo());
//            }
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
        }


    }
