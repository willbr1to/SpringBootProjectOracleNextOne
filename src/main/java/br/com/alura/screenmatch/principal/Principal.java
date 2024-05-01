package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.Episodes;
import br.com.alura.screenmatch.model.EpisodesData;
import br.com.alura.screenmatch.model.SeasonsData;
import br.com.alura.screenmatch.model.SeriesData;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.Deserializador;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;


public class Principal {

    private final String API_KEY = "apikey=7e34390d";
    private final String ENDERECO = "http://www.omdbapi.com/?";
    private ConsumoApi consumoApi = new ConsumoApi();
    private Scanner userInputs = new Scanner(System.in);
    private Deserializador deserializador = new Deserializador();

    public void printMenu() {
        System.out.println("Digite o nome da serie: ");
        var serie = userInputs.nextLine();
        var json = consumoApi.obterDados(ENDERECO + API_KEY + "&t=" + serie.replace(" ", "+"));
        SeriesData serieData = deserializador.deserializa(json, SeriesData.class);
        System.out.println(serieData);

        ArrayList<SeasonsData> temporadas = new ArrayList<>();
        for (int i = 1; i <= serieData.temporadas(); i++) {
            var selectTemporada = "&season=" + i;
            json = consumoApi.obterDados(ENDERECO + API_KEY + "&t=" + serie.replace(" ", "+") + selectTemporada);
            SeasonsData seasonData = deserializador.deserializa(json, SeasonsData.class);
            temporadas.add(seasonData);
        }
//        for( int i = 0; i < serieData.temporadas(); i++) {
//            List<EpisodesData> seasonEps = temporadas.get(i).episodios();
//            System.out.println();
//            System.out.println("Número da temporada: " + temporadas.get(i).temporada());
//            for( int j = 0; j < seasonEps.size(); j++ ){
//                System.out.println(seasonEps.get(j).titulo());
//            }

        List<EpisodesData> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());

        dadosEpisodios.stream()
                .filter(e -> !e.nota().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(EpisodesData::nota).reversed())
                .limit(5)
                .forEach(System.out::println);

        List<Episodes> episodes = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodes(t.temporada(), d))
                ).collect(Collectors.toList());
        episodes.forEach(System.out::println);

        System.out.println("Qual episodio deseja buscar? ");
        String episodioNome = userInputs.nextLine();

        Optional<Episodes> episodioBuscado = episodes.stream()
                .filter(e -> e.getTitulo().toLowerCase().contains(episodioNome.toLowerCase()))
                .findFirst();

       if(episodioBuscado.isPresent()){
           System.out.println("Encontrado! " + episodioBuscado.get());
       } else {
           System.out.println("Não encontrado!");
       }

       Map<Integer, Double> notaTemporadas = episodes.stream()
               .filter(e -> e.getNota() > 0.0)
               .collect(Collectors.groupingBy(Episodes::getTemporada, Collectors.averagingDouble(Episodes::getNota)));
        System.out.println(notaTemporadas);

//        List<String> nomes = Arrays.asList("João", "Maria", "Pedro", "Ana", "Augusto", "amarelo");
//
//  Usando uma implementação de Predicate para filtrar nomes que começam com 'A'
//        Predicate<String> iniciaComA = nome -> nome.startsWith("A");
//        List<String> nomesComA = nomes.stream()
//                .filter(iniciaComA)
//                .collect(Collectors.toList());

//        System.out.println(nomesComA); // [A
//           System.out.println("Episodio não encontrado!");}

//        System.out.println("A partir de qual ano deseja buscar os episodios? ");
//        int anoBusca = userInputs.nextInt();
//        userInputs.nextLine();
//
//        LocalDate dataBusca = LocalDate.of(anoBusca, 1, 1);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
//
//        episodes.stream()
//                .filter(e -> e.getDataLancamento() !=null && e.getDataLancamento().isAfter(dataBusca))
//                //.peek(e -> System.out.println("Peek "+e))
//                .forEach(e -> System.out.println(
//                        "Season: " + e.getTemporada() +
//                                " Episódio: " + e.getTitulo() +
//                                " Data de lancamento: " + e.getDataLancamento().format(formatter)
//                ));
//        LocalDate now = LocalDate.now();
//        System.out.println(now);
    }


}
