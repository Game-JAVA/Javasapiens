package game_models;

import game.Main;
import game_models.Asteroid;
import game_models.Spacecraft;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Phase extends JPanel implements ActionListener {
    private Image background;
    private Spacecraft spacecraft;
    private Timer timer;
    private List<Asteroid> asteroids;
    private boolean inGame;
    private int asteroidsKill;
    private int score;
    private Font gameFont;
    private boolean isPaused;

    // Tudo o que possui a Phase
    public Phase() {
        setFocusable(true);
        setDoubleBuffered(true);

        // Carregar a fonte personalizada
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

        // Inicializa o estado do jogo
        initGame();

        addKeyListener(new TecladoAdapter());

        timer = new Timer(5, this);
        timer.start();
    }

    // Método para inicializar ou reinicializar o jogo
    private void initGame() {
        // Som de fundo do jogo
        Sound.soundgame.loop();

        // Criação da imagem de fundo
        ImageIcon reference = new ImageIcon("res\\background.gif");
        background = reference.getImage();

        // Criação da espaçonave
        spacecraft = new Spacecraft(470, 450);
        spacecraft.load();

        // Criação de asteroides
        asteroids = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Asteroid asteroid = new Asteroid((int) (Math.random() * 1024), (int) (Math.random() * 728) - 728);
            asteroid.load();
            asteroids.add(asteroid);
        }

        inGame = true;
        isPaused = false;
        score = 0;
        asteroidsKill = 0;
    }

    // Colocando gráfico no jogo
    @Override
    public void paint(Graphics g) {
        super.paint(g); // Garante que os componentes da superclasse sejam desenhados
        Graphics2D graficos = (Graphics2D) g;
        //Reconhece se está em jogo
        if (inGame == true) {
            graficos.drawImage(background, 0, 0, null);
            graficos.drawImage(spacecraft.getImage(), spacecraft.getX(), spacecraft.getY(), this);

            // Configura e desenha o texto com a fonte personalizada
            graficos.setFont(gameFont);
            graficos.setColor(Color.WHITE);
            graficos.drawString("Score:" + score, 10, 30);

            //Coloca imagem em todos os asteroids criados e os desenha na tela
            for (int j = 0; j < asteroids.size(); j++) {
                Asteroid asteroid = asteroids.get(j);
                asteroid.load();
                graficos.drawImage(asteroid.getImage(), asteroid.getX(), asteroid.getY(), this);
            }
            List<Shoot> shoots = spacecraft.getShoots();
            //Coloca imagem em todos os tiros criados e os desenha na tela
            for (int i = 0; i < shoots.size(); i++){
                Shoot m = shoots.get(i);
                m.load();
                graficos.drawImage(m.getImage(), m.getX(), m.getY(), this);
            }

            // Exibir mensagem de pausa
            if (isPaused) {
                graficos.setFont(gameFont.deriveFont(Font.BOLD, 40f));
                graficos.setColor(Color.YELLOW);
                graficos.drawString("PAUSED", 400, 330);

                graficos.setFont(gameFont.deriveFont(Font.BOLD, 20f));
                graficos.setColor(Color.WHITE);
                graficos.drawString("Press R to restart", 340, 370);
            }

            Toolkit.getDefaultToolkit().sync(); // Sincroniza a pintura para evitar o tearing
            } else {
                ImageIcon gameOver = new ImageIcon("res\\GameOver.png");
                graficos.drawImage(gameOver.getImage(), 0, 0, null);
                Toolkit.getDefaultToolkit().sync(); // Sincroniza a pintura para evitar o tearing

                // Configura e desenha o texto com a fonte personalizada
                graficos.setFont(gameFont.deriveFont(Font.BOLD, 50f)); // Tamanho maior para o texto de fim de jogo
                graficos.setColor(Color.WHITE);
                graficos.drawString("Score:" + score, 290, 450);

            }
        //Fim de jogo
        if (asteroidsKill == 60){
            inGame = false;
            ImageIcon youWin = new ImageIcon("res\\YouWin.jpeg");
            graficos.drawImage(youWin.getImage(), 0, 0, null);
            Toolkit.getDefaultToolkit().sync(); // Sincroniza a pintura para evitar o tearing
        }
        g.dispose();
    }
    //Checar a colisão do jogo
    public void checkCollisions(){
        Rectangle shapeSpacecraft = spacecraft.getBounds();
        Rectangle shapeShoot;
        Rectangle shapeAsteroid;

        for(int i = 0; i < asteroids.size(); i++){
            Asteroid tempAsteroid = asteroids.get(i);
            shapeAsteroid = tempAsteroid.getBounds();
            if (inGame) {
                //Checar a colisão do asteroid com a espaçonave
                if(shapeSpacecraft.intersects(shapeAsteroid)){
                    tempAsteroid.setVisible(false);
                    Sound.kill.play();
                    inGame = false;
                }
            }
        }
        List<Shoot> shoots = spacecraft.getShoots();
        for (int j = 0; j < shoots.size(); j++){
            Shoot tempShoot = shoots.get(j);
            shapeShoot = tempShoot.getBounds();
            for (int k = 0; k < asteroids.size(); k++){
                Asteroid tempAsteroid = asteroids.get(k);
                shapeAsteroid = tempAsteroid.getBounds();
                if (inGame){
                    //Checar a colisão do tiro com o asteroid
                    if(shapeShoot.intersects(shapeAsteroid)){
                        tempAsteroid.setVisible(false);
                        tempShoot.setVisible(false);
                        score+= 50;
                        asteroidsKill+= 1;
                        if (asteroidsKill == 10){
                            for (int i = 0; i < 20; i++) {
                                Asteroid asteroid = new Asteroid((int) (Math.random() * 1024), (int) (Math.random() * 728) - 728);
                                asteroid.load();
                                asteroids.add(asteroid);
                            }
                        }

                        if (asteroidsKill == 30){
                            for (int i = 0; i < 30; i++) {
                                Asteroid asteroid = new Asteroid((int) (Math.random() * 1024), (int) (Math.random() * 728) - 728);
                                asteroid.load();
                                asteroids.add(asteroid);
                            }
                        }
                        //Som de destruição do asteroid
                        Sound.explosion.play();
                    }
                }
            }
        }
    }

    // Atualiza a localização dos objetos
    @Override
    public void actionPerformed(ActionEvent e) {
        if (isPaused || !inGame) {
            return; // Se estiver pausado ou fora de jogo, não atualiza a lógica do jogo
        }

        spacecraft.move();
        List<Shoot> shoots = spacecraft.getShoots();
        for (int i = 0; i < shoots.size(); i++){
            Shoot m = shoots.get(i);
            if(m.isVisible()){
                m.move();
            }
            else{
                shoots.remove(i);
            }
        }
        for (int j = 0; j < asteroids.size(); j++){
            Asteroid asteroid = asteroids.get(j);
            if(asteroid.isVisible()){
                asteroid.move();
            }
            else {
                asteroids.remove(j);
            }
        }
        checkCollisions();
        repaint();
    }

    // Método para reiniciar o jogo
    private void resetGame() {
        initGame(); // Chama o método para reconfigurar o jogo
        repaint();  // Re-pinta a tela para refletir as mudanças
    }


    // Teclado pressionado
    private class TecladoAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();

            if (code == KeyEvent.VK_ESCAPE) { // Checa se a tecla "esc" foi pressionada
                if (inGame) {
                    isPaused = !isPaused;// Alterna o estado de pausa
                }
            }

            if (code == KeyEvent.VK_R){
                if (isPaused || !inGame){
                    resetGame();
                }
            }

            if (inGame && !isPaused) {
                spacecraft.keyPressed(e);
            }
        }

        // Teclado não pressionado
        @Override
        public void keyReleased(KeyEvent e) {
            spacecraft.keyRelease(e);
        }
    }
}