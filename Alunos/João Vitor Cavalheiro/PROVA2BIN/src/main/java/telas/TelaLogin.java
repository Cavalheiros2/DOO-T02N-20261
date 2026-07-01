package telas;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import objetos.PersistenciaJSON;
import objetos.Usuario;

public class TelaLogin extends JFrame {

    private final JTextField campoNome;
    private final JPasswordField campoSenha;

    private final PersistenciaJSON persistencia = new PersistenciaJSON();

    public TelaLogin() {

        setTitle("Login - Séries TV");
        setSize(420, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        Color corFundo = new Color(35, 39, 47);
        Color corBotao = new Color(255, 107, 107);
        Color corTexto = Color.WHITE;

        JPanel painel = new JPanel();
        painel.setLayout(null);
        painel.setBackground(corFundo);

        JLabel titulo = new JLabel("Sistema de Séries");
        titulo.setBounds(0, 25, 420, 35);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setForeground(corTexto);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        painel.add(titulo);

        JLabel labelNome = new JLabel("Nome:");
        labelNome.setBounds(60, 90, 100, 25);
        labelNome.setForeground(corTexto);
        painel.add(labelNome);

        campoNome = new JTextField();
        campoNome.setBounds(140, 90, 200, 28);
        painel.add(campoNome);

        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setBounds(60, 135, 100, 25);
        labelSenha.setForeground(corTexto);
        painel.add(labelSenha);

        campoSenha = new JPasswordField();
        campoSenha.setBounds(140, 135, 200, 28);
        painel.add(campoSenha);

        JButton botaoEntrar = new JButton("Entrar");
        botaoEntrar.setBounds(70, 200, 120, 35);
        botaoEntrar.setBackground(corBotao);
        botaoEntrar.setForeground(Color.WHITE);
        botaoEntrar.setFocusPainted(false);
        painel.add(botaoEntrar);

        JButton botaoCadastrar = new JButton("Cadastrar");
        botaoCadastrar.setBounds(220, 200, 120, 35);
        botaoCadastrar.setBackground(corBotao);
        botaoCadastrar.setForeground(Color.WHITE);
        botaoCadastrar.setFocusPainted(false);
        painel.add(botaoCadastrar);

        botaoEntrar.addActionListener(e -> entrar());
        botaoCadastrar.addActionListener(e -> cadastrar());

        add(painel);
        setVisible(true);
    }

    private void entrar() {

        String nome = campoNome.getText();
        String senha = new String(campoSenha.getPassword());

        if (nome.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Preencha nome e senha.",
                    "Campos vazios",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Usuario usuario = persistencia.autenticar(nome, senha);

        if (usuario != null) {
            JOptionPane.showMessageDialog(
                    null,
                    "Bem-vindo, " + usuario.getNome() + "!",
                    "Login realizado",
                    JOptionPane.INFORMATION_MESSAGE);

            dispose();

             new TelaPrincipal(usuario);

        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Nome ou senha incorretos.",
                    "Erro no login",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cadastrar() {

        String nome = campoNome.getText();
        String senha = new String(campoSenha.getPassword());

        if (nome.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Preencha nome e senha para cadastrar.",
                    "Campos vazios",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Usuario novoUsuario = new Usuario(nome, senha);

        if (persistencia.cadastrarUsuario(novoUsuario)) {
            JOptionPane.showMessageDialog(
                    null,
                    "Usuário cadastrado com sucesso!",
                    "Cadastro realizado",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Já existe um usuário com esse nome.",
                    "Cadastro não realizado",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}