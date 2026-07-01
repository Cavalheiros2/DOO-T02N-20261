package objetos;

import java.util.ArrayList;
import java.util.List;

public class Usuario {

    private String nome;
    private String senha;
    private List<Serie> favoritos;
    private List<Serie> assistidas;
    private List<Serie> desejaAssistir;

    public Usuario() {
 
    }

    public Usuario(String nome, String senha) {
        this.nome = nome;
        this.senha = senha;
        favoritos = new ArrayList<>();
        assistidas = new ArrayList<>();
        desejaAssistir = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Serie> getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(List<Serie> favoritos) {
        this.favoritos = favoritos;
    }

    public List<Serie> getAssistidas() {
        return assistidas;
    }

    public void setAssistidas(List<Serie> assistidas) {
        this.assistidas = assistidas;
    }

    public List<Serie> getDesejaAssistir() {
        return desejaAssistir;
    }

    public void setDesejaAssistir(List<Serie> desejaAssistir) {
        this.desejaAssistir = desejaAssistir;
    }

    @Override
    public String toString() {
        return "Nome" + nome +
                "Senha" + senha;
    }

}
