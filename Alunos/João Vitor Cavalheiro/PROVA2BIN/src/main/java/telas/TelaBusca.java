package telas;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import objetos.GerenciadorSerie;
import objetos.PersistenciaJSON;
import objetos.Serie;
import objetos.TVMazeService;
import objetos.Usuario;

public class TelaBusca extends JFrame {

    private final Usuario usuario;
    private final GerenciadorSerie gerenciador;
    private final TVMazeService service = new TVMazeService();
    private final PersistenciaJSON persistencia = new PersistenciaJSON();

    private final JTextField campoBusca;
    private final JTable tabelaResultados;
    private final DefaultTableModel modeloTabela;
    private final JTextArea areaDetalhes;

    private List<Serie> resultadosBusca;

    public TelaBusca(Usuario usuario) {
        this.usuario = usuario;
        this.gerenciador = new GerenciadorSerie(usuario);

        setTitle("Busca de Séries");
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Color fundo = new Color(35, 39, 47);
        Color botao = new Color(255, 107, 107);

        JPanel painel = new JPanel(null);
        painel.setBackground(fundo);

        JLabel titulo = new JLabel("Buscar Séries");
        titulo.setBounds(20, 15, 250, 30);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        painel.add(titulo);

        JLabel usuarioLabel = new JLabel("Usuário: " + usuario.getNome());
        usuarioLabel.setBounds(740, 20, 180, 25);
        usuarioLabel.setForeground(Color.WHITE);
        usuarioLabel.setFont(new Font("Arial", Font.BOLD, 14));
        painel.add(usuarioLabel);

        JLabel labelBusca = new JLabel("Nome da série:");
        labelBusca.setBounds(20, 65, 120, 25);
        labelBusca.setForeground(Color.WHITE);
        painel.add(labelBusca);

        campoBusca = new JTextField();
        campoBusca.setBounds(130, 65, 600, 28);
        painel.add(campoBusca);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(750, 65, 120, 28);
        btnBuscar.setBackground(botao);
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFocusPainted(false);
        painel.add(btnBuscar);

        modeloTabela = new DefaultTableModel();
        modeloTabela.addColumn("Nome");
        modeloTabela.addColumn("Idioma");
        modeloTabela.addColumn("Nota");
        modeloTabela.addColumn("Estado");
        modeloTabela.addColumn("Estreia");

        tabelaResultados = new JTable(modeloTabela);
        JScrollPane scrollTabela = new JScrollPane(tabelaResultados);
        scrollTabela.setBounds(20, 120, 500, 300);
        painel.add(scrollTabela);

        areaDetalhes = new JTextArea();
        areaDetalhes.setEditable(false);
        areaDetalhes.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane scrollDetalhes = new JScrollPane(areaDetalhes);
        scrollDetalhes.setBounds(540, 120, 370, 300);
        painel.add(scrollDetalhes);

        JButton btnFavorito = new JButton("Adicionar aos favoritos");
        btnFavorito.setBounds(20, 450, 260, 35);
        btnFavorito.setBackground(botao);
        btnFavorito.setForeground(Color.WHITE);
        painel.add(btnFavorito);

        JButton btnAssistida = new JButton("Adicionar às assistidas");
        btnAssistida.setBounds(330, 450, 260, 35);
        btnAssistida.setBackground(botao);
        btnAssistida.setForeground(Color.WHITE);
        painel.add(btnAssistida);

        JButton btnDesejo = new JButton("Desejo assistir");
        btnDesejo.setBounds(650, 450, 260, 35);
        btnDesejo.setBackground(botao);
        btnDesejo.setForeground(Color.WHITE);
        painel.add(btnDesejo);

        JButton btnVoltar = new JButton("Voltar ao menu");
        btnVoltar.setBounds(20, 510, 890, 35);
        btnVoltar.setBackground(botao);
        btnVoltar.setForeground(Color.WHITE);
        painel.add(btnVoltar);

        btnBuscar.addActionListener(e -> buscarSeries());

        tabelaResultados.getSelectionModel().addListSelectionListener(e -> mostrarDetalhes());

        btnFavorito.addActionListener(e -> adicionarFavorito());
        btnAssistida.addActionListener(e -> adicionarAssistida());
        btnDesejo.addActionListener(e -> adicionarDesejo());

        btnVoltar.addActionListener(e -> {
            new TelaPrincipal(usuario);
            dispose();
        });

        add(painel);
        setVisible(true);
    }

    private void buscarSeries() {
        String nome = campoBusca.getText();

        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite o nome de uma série.");
            return;
        }

        resultadosBusca = service.buscarSeries(nome);
        modeloTabela.setRowCount(0);

        for (Serie serie : resultadosBusca) {
            modeloTabela.addRow(new Object[]{
                    serie.getNome(),
                    serie.getIdioma(),
                    serie.getNota(),
                    serie.getStatus(),
                    serie.getDataEstreia()
            });
        }
    }

    private Serie getSerieSelecionada() {
        int linha = tabelaResultados.getSelectedRow();

        if (linha == -1) {
            JOptionPane.showMessageDialog(null, "Selecione uma série primeiro.");
            return null;
        }

        return resultadosBusca.get(linha);
    }

    private void mostrarDetalhes() {
        int linha = tabelaResultados.getSelectedRow();

        if (linha == -1 || resultadosBusca == null) {
            return;
        }

        Serie serie = resultadosBusca.get(linha);

        areaDetalhes.setText(
                "Nome: " + serie.getNome() +
                        "\nIdioma: " + serie.getIdioma() +
                        "\nGêneros: " + serie.getGeneros() +
                        "\nNota: " + serie.getNota() +
                        "\nEstado: " + serie.getStatus() +
                        "\nEstreia: " + serie.getDataEstreia() +
                        "\nTérmino: " + serie.getDataFim() +
                        "\nEmissora: " + serie.getEmissora()
        );
    }

    private void adicionarFavorito() {
        Serie serie = getSerieSelecionada();

        if (serie != null && gerenciador.adicionarFavorito(serie)) {
            salvarUsuarioAtual();
            JOptionPane.showMessageDialog(null, "Série adicionada aos favoritos.");
        } else if (serie != null) {
            JOptionPane.showMessageDialog(null, "Essa série já está nos favoritos.");
        }
    }

    private void adicionarAssistida() {
        Serie serie = getSerieSelecionada();

        if (serie != null && gerenciador.adicionarAssistida(serie)) {
            salvarUsuarioAtual();
            JOptionPane.showMessageDialog(null, "Série adicionada às assistidas.");
        } else if (serie != null) {
            JOptionPane.showMessageDialog(null, "Essa série já está nas assistidas.");
        }
    }

    private void adicionarDesejo() {
        Serie serie = getSerieSelecionada();

        if (serie != null && gerenciador.adicionarDesejaAssistir(serie)) {
            salvarUsuarioAtual();
            JOptionPane.showMessageDialog(null, "Série adicionada ao desejo.");
        } else if (serie != null) {
            JOptionPane.showMessageDialog(null, "Essa série já está no desejo.");
        }
    }

    private void salvarUsuarioAtual() {
        List<Usuario> usuarios = persistencia.carregarUsuarios();

        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getNome().equals(usuario.getNome())) {
                usuarios.set(i, usuario);
                break;
            }
        }

        persistencia.salvarUsuarios(usuarios);
    }
}