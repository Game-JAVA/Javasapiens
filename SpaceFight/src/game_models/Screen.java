package game_models;

import game.Main;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Screen extends JPanel implements ActionListener {
    private JButton startButton;
    private JButton aboutButton;
    private JButton exitButton;
    private JButton backButton;
    private Main main;
    private Font gameFont;

    public Screen(Main main) {
        this.main = main;
        setLayout(null); // Usamos layout absoluto para posicionar os componentes manualmente

        loadFont(); // Carrega a fonte personalizada

        // Inicializa e adiciona os botões ao painel
        startButton = createButton("JOGAR", 400, 400, 200, 50);
        aboutButton = createButton("SOBRE", 400, 460, 200, 50);
        exitButton = createButton("SAIR", 400, 520, 200, 50);

        // Adiciona os botões ao painel
        add(startButton);
        add(aboutButton);
        add(exitButton);

        // Inicializa o botão "BACK"
        backButton = createButton("BACK", 0, 0, 200, 50); // Posição inicial arbitrária
        backButton.addActionListener(this);
    }

    private void loadFont() {
        try {
            gameFont = Font.createFont(Font.TRUETYPE_FONT, new File("res/PressStart2P-Regular.ttf")).deriveFont(Font.BOLD, 20f);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(gameFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            gameFont = new Font("Helvetica", Font.BOLD, 20);
        }
    }

    private JButton createButton(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setFont(gameFont);
        button.setBounds(x, y, width, height);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setUI(new CustomButtonUI());
        button.addActionListener(this);
        return button;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon screenHome = new ImageIcon("res/telainicio.png");
        g.drawImage(screenHome.getImage(), 0, 0, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            showGamePhase();
        } else if (e.getSource() == aboutButton) {
            showAboutScreen();
        } else if (e.getSource() == exitButton) {
            System.exit(0);
        } else if (e.getSource() == backButton) {
            showHomeScreen();
        }
    }

    private void showGamePhase() {
        main.getContentPane().removeAll();
        Phase phase = new Phase();
        main.getContentPane().add(phase);
        main.revalidate();
        main.repaint();
        phase.requestFocusInWindow();
    }

    private void showAboutScreen() {
        main.getContentPane().removeAll();

        // Cria um JLabel para exibir a imagem
        ImageIcon aboutImage = new ImageIcon("res/tela_aboutscreen.png");
        JLabel imageLabel = new JLabel(aboutImage);
        backButton.setBounds(790, 500, 200, 50);

        imageLabel.setBounds(0, 0, aboutImage.getIconWidth(), aboutImage.getIconHeight());

        // Cria um novo painel para a tela "Sobre"
        JPanel aboutPanel = new JPanel(null);
        aboutPanel.add(backButton);
        aboutPanel.add(imageLabel);

        // Ajusta a posição do botão "BACK" e o adiciona ao painel "Sobre"

        // Adiciona o painel "Sobre" à janela principal
        main.getContentPane().add(aboutPanel);

        // Revalida e redesenha a janela principal
        main.revalidate();
        main.repaint();
    }

    private void showHomeScreen() {
        main.getContentPane().removeAll();
        main.getContentPane().add(new Screen(main));
        main.revalidate();
        main.repaint();
    }

    // Classe interna para definir a UI personalizada dos botões
    private class CustomButtonUI extends BasicButtonUI {
        @Override
        public void installDefaults(AbstractButton b) {
            super.installDefaults(b);
            b.setBackground(Color.YELLOW);
            b.setContentAreaFilled(false);
            b.setFocusPainted(false);
            b.setBorderPainted(false);
        }

        @Override
        public void paint(Graphics g, JComponent c) {
            AbstractButton b = (AbstractButton) c;
            ButtonModel model = b.getModel();

            if (model.isPressed()) {
                g.setColor(b.getBackground());
            } else {
                g.setColor(b.getBackground());
            }

            g.fillRect(0, 0, c.getWidth(), c.getHeight());
            super.paint(g, c);
        }
    }
}
