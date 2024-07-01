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
    private Timer rtimer;
    private List<Asteroid> asteroids;
    private boolean inGame;
    private int asteroidsKill;
    private int score;
    private Font gameFont;
    private boolean isPaused;
    private boolean showRoundText = false;
    private long roundTextStartTime;
    private final int roundTextDuration = 2000; // duração em milissegundos (2 segundos)

    public Phase() {
        setFocusable(true);
        setDoubleBuffered(true);

        try {
            gameFont = Font.createFont(Font.TRUETYPE_FONT, new File("res/PressStart2P-Regular.ttf"));
            gameFont = gameFont.deriveFont(Font.BOLD, 20f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(gameFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            gameFont = new Font("Helvetica", Font.BOLD, 20);
        }

        initGame();
        addKeyListener(new TecladoAdapter());

        timer = new Timer(5, this);
        timer.start();
    }

    private void initGame() {
        Sound.soundgame.loop();

        ImageIcon reference = new ImageIcon("res\\background.gif");
        background = reference.getImage();

        spacecraft = new Spacecraft(470, 450);
        spacecraft.load();

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

        // Inicia o timer para mostrar o texto do round
        if (asteroidsKill == 0) {
            showRoundText = true;
            roundTextStartTime = System.currentTimeMillis();
            rtimer = new Timer(50, this);
            rtimer.start();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D graficos = (Graphics2D) g;

        if (inGame) {
            graficos.drawImage(background, 0, 0, null);
            graficos.drawImage(spacecraft.getImage(), spacecraft.getX(), spacecraft.getY(), this);

            graficos.setFont(gameFont);
            graficos.setColor(Color.WHITE);
            graficos.drawString("Score:" + score, 10, 30);

            for (int j = 0; j < asteroids.size(); j++) {
                Asteroid asteroid = asteroids.get(j);
                asteroid.load();
                graficos.drawImage(asteroid.getImage(), asteroid.getX(), asteroid.getY(), this);
            }

            List<Shoot> shoots = spacecraft.getShoots();
            for (int i = 0; i < shoots.size(); i++) {
                Shoot m = shoots.get(i);
                m.load();
                graficos.drawImage(m.getImage(), m.getX(), m.getY(), this);
            }

            if (isPaused) {
                graficos.setFont(gameFont.deriveFont(Font.BOLD, 40f));
                graficos.setColor(Color.YELLOW);
                graficos.drawString("PAUSED", 400, 330);

                graficos.setFont(gameFont.deriveFont(Font.BOLD, 20f));
                graficos.setColor(Color.WHITE);
                graficos.drawString("Press R to restart", 340, 370);
            }

            if (showRoundText) {
                graficos.setFont(gameFont.deriveFont(Font.BOLD, 20f));
                graficos.setColor(Color.WHITE);
                graficos.drawString("Round 1", 440, 364);
            }

            Toolkit.getDefaultToolkit().sync();
        } else {
            ImageIcon gameOver = new ImageIcon("res\\GameOver.png");
            graficos.drawImage(gameOver.getImage(), 0, 0, null);
            Toolkit.getDefaultToolkit().sync();

            graficos.setFont(gameFont.deriveFont(Font.BOLD, 40f));
            graficos.setColor(Color.WHITE);
            graficos.drawString("Score:" + score, 340, 435);

            graficos.setFont(gameFont.deriveFont(Font.BOLD, 20f));
            graficos.setColor(Color.WHITE);
            graficos.drawString("Press R to restart", 340, 470);
        }

        if (asteroidsKill == 150) {
            inGame = false;
            ImageIcon youWin = new ImageIcon("res\\YouWin.jpeg");
            graficos.drawImage(youWin.getImage(), 0, 0, null);
            Toolkit.getDefaultToolkit().sync();

            graficos.setFont(gameFont.deriveFont(Font.BOLD, 20f));
            graficos.setColor(Color.WHITE);
            graficos.drawString("Press R to restart", 325, 485);
        }
        g.dispose();
    }

    public void checkCollisions() {
        Rectangle shapeSpacecraft = spacecraft.getBounds();
        Rectangle shapeShoot;
        Rectangle shapeAsteroid;

        for (int i = 0; i < asteroids.size(); i++) {
            Asteroid tempAsteroid = asteroids.get(i);
            shapeAsteroid = tempAsteroid.getBounds();
            if (inGame) {
                if (shapeSpacecraft.intersects(shapeAsteroid)) {
                    tempAsteroid.setVisible(false);
                    Sound.kill.play();
                    inGame = false;
                }
            }
        }

        List<Shoot> shoots = spacecraft.getShoots();
        for (int j = 0; j < shoots.size(); j++) {
            Shoot tempShoot = shoots.get(j);
            shapeShoot = tempShoot.getBounds();
            for (int k = 0; k < asteroids.size(); k++) {
                Asteroid tempAsteroid = asteroids.get(k);
                shapeAsteroid = tempAsteroid.getBounds();
                if (inGame) {
                    if (shapeShoot.intersects(shapeAsteroid)) {
                        tempAsteroid.setVisible(false);
                        tempShoot.setVisible(false);
                        score += 50;
                        asteroidsKill += 1;

                        if (asteroidsKill == 10) {
                            for (int i = 0; i < 20; i++) {
                                Asteroid asteroid = new Asteroid((int) (Math.random() * 1024), (int) (Math.random() * 728) - 728);
                                asteroid.load();
                                asteroids.add(asteroid);
                            }
                        }

                        if (asteroidsKill == 30) {
                            for (int i = 0; i < 30; i++) {
                                Asteroid asteroid = new Asteroid((int) (Math.random() * 1024), (int) (Math.random() * 728) - 728);
                                asteroid.load();
                                asteroids.add(asteroid);
                            }
                        }

                        if (asteroidsKill == 60) {
                            for (int i = 0; i < 40; i++) {
                                Asteroid asteroid = new Asteroid((int) (Math.random() * 1024), (int) (Math.random() * 728) - 728);
                                asteroid.load();
                                asteroids.add(asteroid);
                            }
                        }

                        if (asteroidsKill == 100) {
                            for (int i = 0; i < 50; i++) {
                                Asteroid asteroid = new Asteroid((int) (Math.random() * 1024), (int) (Math.random() * 728) - 728);
                                asteroid.load();
                                asteroids.add(asteroid);
                            }
                        }

                        Sound.explosion.play();
                    }
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isPaused || !inGame) {
            return;
        }

        spacecraft.move();
        List<Shoot> shoots = spacecraft.getShoots();
        for (int i = 0; i < shoots.size(); i++) {
            Shoot m = shoots.get(i);
            if (m.isVisible()) {
                m.move();
            } else {
                shoots.remove(i);
            }
        }

        for (int j = 0; j < asteroids.size(); j++) {
            Asteroid asteroid = asteroids.get(j);
            if (asteroid.isVisible()) {
                asteroid.move();
            } else {
                asteroids.remove(j);
            }
        }

        if (showRoundText && System.currentTimeMillis() - roundTextStartTime >= roundTextDuration) {
            showRoundText = false;
            rtimer.stop();
        }

        checkCollisions();
        repaint();
    }

    private void resetGame() {
        initGame();
        repaint();
    }

    private class TecladoAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();

            if (code == KeyEvent.VK_ESCAPE) {
                if (inGame) {
                    isPaused = !isPaused;
                }
            }

            if (code == KeyEvent.VK_R) {
                if (isPaused || !inGame) {
                    resetGame();
                }
            }

            if (inGame && !isPaused) {
                spacecraft.keyPressed(e);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            spacecraft.keyRelease(e);
        }
    }
}
