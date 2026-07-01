package objetos;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TVMazeService {

    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public List<Serie> buscarSeries(String nome) {

        List<Serie> seriesEncontradas = new ArrayList<>();

        try {
            String nomeFormatado = URLEncoder.encode(nome, StandardCharsets.UTF_8);
            String url = "https://api.tvmaze.com/search/shows?q=" + nomeFormatado;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = CLIENT.send(
                    request,
                    HttpResponse.BodyHandlers.ofString());

            JsonNode raiz = mapper.readTree(response.body());

            for (JsonNode resultado : raiz) {

                JsonNode show = resultado.get("show");

                int id = show.path("id").asInt();

                String nomeSerie = show.path("name").asText("Não informado");

                String idioma = show.path("language").asText("Não informado");

                List<String> generos = new ArrayList<>();
                for (JsonNode genero : show.path("genres")) {
                    generos.add(genero.asText());
                }

                Double nota = show.path("rating")
                        .path("average")
                        .asDouble(0.0);

                String status = show.path("status").asText("Não informado");

                String dataEstreia = show.path("premiered").asText("Não informado");

                String dataFim = show.path("ended").asText("Não informado");

                String emissora = show.path("network")
                        .path("name")
                        .asText("Não informado");

                Serie serie = new Serie(
                        nomeSerie,
                        idioma,
                        generos,
                        nota,
                        status,
                        dataEstreia,
                        dataFim,
                        emissora,
                        id);

                seriesEncontradas.add(serie);
            }

        } catch (IOException | InterruptedException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Não foi possível conectar à TVMaze.\nVerifique sua conexão com a internet.",
                    "Erro na busca",
                    JOptionPane.ERROR_MESSAGE);
        }

        return seriesEncontradas;

    }

}
