package game_models;

import game.Main;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Home extends JPanel implements ActionListener {
    private JButton startButton;
    private JButton aboutButton;
    private JButton exitButton;
    private Main main;
    private Image background;
    private Font gameFont;

    public Home(Main main) {
        this.main = main;
        setLayout(null); // Usaremos layout absoluto para posicionar os componentes manualmente

        try {
            // Carrega a fonte a partir do arquivo
            gameFont = Font.createFont(Font.TRUETYPE_FONT, new File("res/PressStart2P-Regular.ttf"));
            // Define o tamanho padrão da fonte
            gameFont = gameFont.deriveFont(Font.BOLD, 20f);
            // Registra a fonte no sistema
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(gameFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            // Se houver um problema ao carregar a fonte, usa uma fonte padrão
            gameFont = new Font("Helvetica", Font.BOLD, 20);
        }

        // Botão de iniciar jogo
        startButton = new JButton("JOGAR");
        startButton.setFont(gameFont);
        startButton.setBounds(400, 400, 200, 50); // Posição e tamanho do botão
        startButton.setBackground(Color.yellow);
        startButton.addActionListener(this); // Adiciona o listener de ação ao botão
        startButton.setFocusPainted(false);
        add(startButton); // Adiciona o botão ao painel
        // Botão sobre
        aboutButton = new JButton("SOBRE");
        aboutButton.setFont(gameFont);
        aboutButton.setBounds(400, 460, 200, 50); // Posição e tamanho do botão
        aboutButton.setBackground(Color.yellow);
        aboutButton.addActionListener(this); // Adiciona o listener de ação ao botão
        aboutButton.setFocusPainted(false);
        add(aboutButton); // Adiciona o botão ao painel
        // Botão sair
        exitButton = new JButton("SAIR");
        exitButton.setFont(gameFont);
        exitButton.setBounds(400, 520, 200, 50); // Posição e tamanho do botão
        //exitButton.setCursor(new Cursor(Cursor.)); colocar cursor especial
        exitButton.setBackground(Color.yellow);
        exitButton.addActionListener(this); // Adiciona o listener de ação ao botão
        exitButton.setFocusPainted(false);
        add(exitButton); // Adiciona o botão ao painel
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Desenha o fundo ou qualquer outra arte do menu inicial, se necessário
        ImageIcon screenHome = new ImageIcon("res\\telainicio.png");
        g.drawImage(screenHome.getImage(), 0, 0, null);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            // Remove o painel atual (Home) da janela principal
            main.getContentPane().remove(this);

            // Adiciona a fase do jogo à janela principal
            Phase phase = new Phase();
            main.getContentPane().add(phase);

            // Revalida e redesenha a janela principal
            main.revalidate();
            main.repaint();

            // Solicita o foco de entrada para a fase do jogo
            phase.requestFocusInWindow();

            // Inicia a lógica de jogo, se necessário
            // Exemplo: phase.initGame(); // Certifique-se de chamar o método de inicialização da fase aqui, se necessário
        }
    }
}
