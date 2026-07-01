package objetos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Serie {
    private String nome;
    private String idioma;
    private List<String> generos;
    private Double nota;
    private String status;
    private String dataEstreia;
    private String dataFim;
    private String emissora;
    private int id;

    public Serie() {

    }

    public Serie(String nome, String idioma, List<String> generos, Double nota, String status, String dataEstreia,
            String dataFim, String emissora, int id) {
        this.nome = nome;
        this.idioma = idioma;
        this.generos = generos;
        this.nota = nota;
        this.status = status;
        this.dataEstreia = dataEstreia;
        this.dataFim = dataFim;
        this.emissora = emissora;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public List<String> getGeneros() {
        return generos;
    }

    public void setGeneros(List<String> generos) {
        this.generos = generos;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDataEstreia() {
        return dataEstreia;
    }

    public void setDataEstreia(String dataEstreia) {
        this.dataEstreia = dataEstreia;
    }

    public String getDataFim() {
        return dataFim;
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }

    public String getEmissora() {
        return emissora;
    }

    public void setEmissora(String emissora) {
        this.emissora = emissora;
    }

    @JsonIgnore
    public int getPrioridadeStatus() {
        if (status.equals("Running")) {
            return 1;
        }

        if (status.equals("Ended")) {
            return 2;
        }

        if (status.equals("Canceled")) {
            return 3;
        }

        return 4;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Serie outraSerie = (Serie) obj;

        return this.id == outraSerie.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
        return "Emissora:" + emissora +
                "DataEstreia:" + dataEstreia +
                "DataFim:" + dataFim +
                "Status:" + status +
                "Nota:" + nota +
                "Gênero:" + generos +
                "Nome:" + nome +
                "Idioma:" + idioma;
    }
}
