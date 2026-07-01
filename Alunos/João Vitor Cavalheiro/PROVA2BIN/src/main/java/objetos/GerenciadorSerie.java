package objetos;

import java.util.Comparator;
import java.util.List;

public class GerenciadorSerie {
    private Usuario usuario;

    public GerenciadorSerie(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuarioAtual) {
        this.usuario = usuarioAtual;
    }

    public boolean adicionarFavorito(Serie serie) {
        if (!usuario.getFavoritos().contains(serie)) {

            return usuario.getFavoritos().add(serie);

        }
        return false;
    }

    public boolean removerFavorito(Serie serie) {

        return usuario.getFavoritos().remove(serie);

    }

    public boolean adicionarAssistida(Serie serie) {
        if (!usuario.getAssistidas().contains(serie)) {

            removerDesejaAssistir(serie);

            return usuario.getAssistidas().add(serie);

        }
        return false;
    }

    public boolean removerAssistida(Serie serie) {

        return usuario.getAssistidas().remove(serie);

    }

    public boolean adicionarDesejaAssistir(Serie serie) {
        if (!usuario.getDesejaAssistir().contains(serie)) {

            removerAssistida(serie);

            return usuario.getDesejaAssistir().add(serie);

        }
        return false;
    }

    public boolean removerDesejaAssistir(Serie serie) {

        return usuario.getDesejaAssistir().remove(serie);

    }

    public void ordemAlfabetica(List<Serie> lista) {
        lista.sort(Comparator.comparing(Serie::getNome));
    }

    public void ordemNota(List<Serie> lista) {
        lista.sort(Comparator.comparing(Serie::getNota));
    }

    public void ordemStatus(List<Serie> lista) {
        lista.sort(Comparator.comparingInt(Serie::getPrioridadeStatus));
    }

    public void ordemEstreia(List<Serie> lista) {
        lista.sort(Comparator.comparing(Serie::getDataEstreia));
    }

}
