package objetos;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PersistenciaJSON {

    private final ObjectMapper mapper = new ObjectMapper();
    private final File arquivo = new File("dados/usuarios.json");

    public List<Usuario> carregarUsuarios() {
        try {

            if (!arquivo.exists()) {
                return new ArrayList<>();
            }

            return mapper.readValue(
                    arquivo,
                    new TypeReference<List<Usuario>>() {
                    });

        } catch (IOException erro) {
            JOptionPane.showMessageDialog(null,
                    "Não foi possível carregar os dados dos usuários",
                    "Erro ao carregar usuários",
                    JOptionPane.ERROR_MESSAGE);
            return new ArrayList<>();
        }
    }

    public void salvarUsuarios(List<Usuario> usuarios) {
        try {

            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(arquivo, usuarios);

        } catch (IOException erro) {
            JOptionPane.showMessageDialog(
                    null,
                    "Não foi possível salvar os dados dos usuários.",
                    "Erro ao salvar usuários",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void buscarUsuario() {
        carregarUsuarios();
        
    }

    public void autenticar() {

    }

    public void cadastrarUsuario() {

    }
}
