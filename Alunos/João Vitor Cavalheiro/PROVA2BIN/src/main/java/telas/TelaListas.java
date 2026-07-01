package telas;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import objetos.GerenciadorSerie;
import objetos.PersistenciaJSON;
import objetos.Serie;
import objetos.Usuario;

public class TelaListas extends JFrame {

    private final Usuario usuario;
    private final GerenciadorSerie gerenciador;
    private final PersistenciaJSON persistencia = new PersistenciaJSON();

    private final JTabbedPane abas;
    private final JComboBox<String> comboOrdenacao;

    private final JTable tabelaFavoritos;
    private final JTable tabelaAssistidas;
    private final JTable tabelaDesejo;

    private final DefaultTableModel modeloFavoritos;
    private final DefaultTableModel modeloAssistidas;
    private final DefaultTableModel modeloDesejo;

    public TelaListas(Usuario usuario) {
        this.usuario = usuario;
        this.gerenciador = new GerenciadorSerie(usuario);

        setTitle("Minhas Listas");
        setSize(950, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        Color fundo = new Color(35, 39, 47);
        Color botao = new Color(255, 107, 107);

        JPanel painel = new JPanel(null);
        painel.setBackground(fundo);

        JLabel titulo = new JLabel("Minhas Séries");
        titulo.setBounds(20, 15, 250, 30);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        painel.add(titulo);

        JLabel usuarioLabel = new JLabel("Usuário: " + usuario.getNome());
        usuarioLabel.setBounds(720, 20, 180, 25);
        usuarioLabel.setForeground(Color.WHITE);
        usuarioLabel.setFont(new Font("Arial", Font.BOLD, 14));
        painel.add(usuarioLabel);

        JLabel labelOrdenar = new JLabel("Ordenar por:");
        labelOrdenar.setBounds(20, 65, 100, 25);
        labelOrdenar.setForeground(Color.WHITE);
        painel.add(labelOrdenar);

        comboOrdenacao = new JComboBox<>(new String[]{"Nome", "Nota", "Estado", "Estreia"});
        comboOrdenacao.setBounds(110, 65, 140, 28);
        painel.add(comboOrdenacao);

        JButton btnOrdenar = new JButton("Ordenar");
        btnOrdenar.setBounds(270, 65, 120, 28);
        btnOrdenar.setBackground(botao);
        btnOrdenar.setForeground(Color.WHITE);
        painel.add(btnOrdenar);

        abas = new JTabbedPane();
        abas.setBounds(20, 110, 890, 330);

        modeloFavoritos = criarModelo();
        modeloAssistidas = criarModelo();
        modeloDesejo = criarModelo();

        tabelaFavoritos = new JTable(modeloFavoritos);
        tabelaAssistidas = new JTable(modeloAssistidas);
        tabelaDesejo = new JTable(modeloDesejo);

        abas.addTab("Favoritos", new JScrollPane(tabelaFavoritos));
        abas.addTab("Assistidas", new JScrollPane(tabelaAssistidas));
        abas.addTab("Desejo assistir", new JScrollPane(tabelaDesejo));

        painel.add(abas);

        JButton btnRemover = new JButton("Remover da lista");
        btnRemover.setBounds(20, 460, 430, 35);
        btnRemover.setBackground(botao);
        btnRemover.setForeground(Color.WHITE);
        painel.add(btnRemover);

        JButton btnVoltar = new JButton("Voltar ao menu");
        btnVoltar.setBounds(480, 460, 430, 35);
        btnVoltar.setBackground(botao);
        btnVoltar.setForeground(Color.WHITE);
        painel.add(btnVoltar);

        btnOrdenar.addActionListener(e -> ordenarListaAtual());
        btnRemover.addActionListener(e -> removerSelecionada());

        btnVoltar.addActionListener(e -> {
            new TelaPrincipal(usuario);
            dispose();
        });

        add(painel);
        setVisible(true);

        atualizarTudo();
    }

    private DefaultTableModel criarModelo() {
        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("Nome");
        modelo.addColumn("Idioma");
        modelo.addColumn("Gêneros");
        modelo.addColumn("Nota");
        modelo.addColumn("Estado");
        modelo.addColumn("Estreia");
        modelo.addColumn("Término");
        modelo.addColumn("Emissora");

        return modelo;
    }

    private JTable getTabelaAtual() {
        int aba = abas.getSelectedIndex();

        if (aba == 0) {
            return tabelaFavoritos;
        }

        if (aba == 1) {
            return tabelaAssistidas;
        }

        return tabelaDesejo;
    }

    private List<Serie> getListaAtual() {
        int aba = abas.getSelectedIndex();

        if (aba == 0) {
            return usuario.getFavoritos();
        }

        if (aba == 1) {
            return usuario.getAssistidas();
        }

        return usuario.getDesejaAssistir();
    }

    private void preencherTabela(DefaultTableModel modelo, List<Serie> lista) {
        modelo.setRowCount(0);

        for (Serie serie : lista) {
            modelo.addRow(new Object[]{
                    serie.getNome(),
                    serie.getIdioma(),
                    serie.getGeneros(),
                    serie.getNota(),
                    serie.getStatus(),
                    serie.getDataEstreia(),
                    serie.getDataFim(),
                    serie.getEmissora()
            });
        }
    }

    private void atualizarTudo() {
        preencherTabela(modeloFavoritos, usuario.getFavoritos());
        preencherTabela(modeloAssistidas, usuario.getAssistidas());
        preencherTabela(modeloDesejo, usuario.getDesejaAssistir());
    }

    private void ordenarListaAtual() {
        List<Serie> lista = getListaAtual();
        String opcao = (String) comboOrdenacao.getSelectedItem();

        switch (opcao) {
            case "Nome" -> gerenciador.ordemAlfabetica(lista);
            case "Nota" -> gerenciador.ordemNota(lista);
            case "Estado" -> gerenciador.ordemStatus(lista);
            case "Estreia" -> gerenciador.ordemEstreia(lista);
        }

        salvarUsuarioAtual();
        atualizarTudo();
    }

    private void removerSelecionada() {
        JTable tabelaAtual = getTabelaAtual();
        int linha = tabelaAtual.getSelectedRow();

        if (linha == -1) {
            JOptionPane.showMessageDialog(null, "Selecione uma série para remover.");
            return;
        }

        Serie serie = getListaAtual().get(linha);
        int aba = abas.getSelectedIndex();

        switch (aba) {
            case 0 -> gerenciador.removerFavorito(serie);
            case 1 -> gerenciador.removerAssistida(serie);
            default -> gerenciador.removerDesejaAssistir(serie);
        }

        salvarUsuarioAtual();
        atualizarTudo();

        JOptionPane.showMessageDialog(null, "Série removida com sucesso.");
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