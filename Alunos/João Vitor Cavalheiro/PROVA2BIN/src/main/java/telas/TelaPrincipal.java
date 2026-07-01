package telas;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import objetos.Usuario;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal(Usuario usuario) {
        setTitle("Sistema de Séries");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        Color fundo = new Color(35, 39, 47);
        Color botao = new Color(255, 107, 107);

        JPanel painel = new JPanel(null);
        painel.setBackground(fundo);

        JLabel titulo = new JLabel("Bem-vindo, " + usuario.getNome());
        titulo.setBounds(0, 30, 500, 35);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        painel.add(titulo);

        JButton btnBuscar = new JButton("Buscar Séries");
        btnBuscar.setBounds(150, 100, 200, 40);
        btnBuscar.setBackground(botao);
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFocusPainted(false);
        painel.add(btnBuscar);

        JButton btnListas = new JButton("Minhas Listas");
        btnListas.setBounds(150, 160, 200, 40);
        btnListas.setBackground(botao);
        btnListas.setForeground(Color.WHITE);
        btnListas.setFocusPainted(false);
        painel.add(btnListas);

        JButton btnSair = new JButton("Sair");
        btnSair.setBounds(150, 220, 200, 40);
        btnSair.setBackground(botao);
        btnSair.setForeground(Color.WHITE);
        btnSair.setFocusPainted(false);
        painel.add(btnSair);

        btnBuscar.addActionListener(e -> {
            new TelaBusca(usuario);
            dispose();
        });

        btnListas.addActionListener(e -> {
            new TelaListas(usuario);
            dispose();
        });

        btnSair.addActionListener(e -> System.exit(0));

        add(painel);
        setVisible(true);
    }
}