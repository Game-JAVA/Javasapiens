package game_models;

import game_models.Asteroid;
import game_models.Spacecraft;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Phase extends JPanel implements ActionListener {
    private Image background;
    private Spacecraft spacecraft;
    private Timer timer;
    private List<Asteroid> asteroids;
    private boolean inGame;

    // Tudo o que possui a Phase
    public Phase() {
        setFocusable(true);
        setDoubleBuffered(true);

        ImageIcon reference = new ImageIcon("res\\background.gif");
        background = reference.getImage();

        spacecraft = new Spacecraft(360, 450);
        spacecraft.load();

        addKeyListener(new TecladoAdapter());

        asteroids = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Asteroid asteroid = new Asteroid((int) (Math.random() * 1024), (int) (Math.random() * 728) - 728);
            asteroid.load();
            asteroids.add(asteroid);
        }

        timer = new Timer(5, this);
        timer.start();
        inGame = true;
    }

    // Colocando gráfico no background e no player para serem utilizados
    @Override
    public void paint(Graphics g) {
        super.paint(g); // Garante que os componentes da superclasse sejam desenhados
        Graphics2D graficos = (Graphics2D) g;
        if (inGame == true) {
            graficos.drawImage(background, 0, 0, null);
            graficos.drawImage(spacecraft.getImage(), spacecraft.getX(), spacecraft.getY(), this);

            for (Asteroid asteroid : asteroids) {
                graficos.drawImage(asteroid.getImage(), asteroid.getX(), asteroid.getY(), this);
            }

            List<Shoot> shoots = spacecraft.getShoots();
            for (int i = 0; i < shoots.size(); i++) {
                Shoot m = shoots.get(i);
                m.load();
                graficos.drawImage(m.getImage(), m.getX(), m.getY(), this);
            }
            Toolkit.getDefaultToolkit().sync(); // Sincroniza a pintura para evitar o tearing
        } else {
            ImageIcon gameOver = new ImageIcon("res\\GameOver.jpg");
            graficos.drawImage(gameOver.getImage(), 0, 0, null);
            Toolkit.getDefaultToolkit().sync(); // Sincroniza a pintura para evitar o tearing
        }
        g.dispose();
    }

    public void checkCollisions(){
        Rectangle shapeSpacecraft = spacecraft.getBounds();
        Rectangle shapeAsteroid;

        for(int i = 0; i < asteroids.size(); i++){
            Asteroid tempAsteroid = asteroids.get(i);
            shapeAsteroid = tempAsteroid.getBounds();
            if(shapeSpacecraft.intersects(shapeAsteroid)){
                spacecraft.setVisible(false);
                tempAsteroid.setVisible(false);
                inGame = false;
            }
        }

    }

    // Atualiza a localização dos objetos
    @Override
    public void actionPerformed(ActionEvent e) {
        spacecraft.move();
        List<Shoot> shoots = spacecraft.getShoots();
        for (int i = 0; i < shoots.size(); i++){
            Shoot m = shoots.get(i);
            m.move();
        }
        for (Asteroid asteroid : asteroids) {
            asteroid.move();
        }
        checkCollisions();
        repaint();
    }

    // Teclado pressionado
    private class TecladoAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            spacecraft.keyPressed(e);
        }

        // Teclado não pressionado
        @Override
        public void keyReleased(KeyEvent e) {
            spacecraft.keyRelease(e);
        }
    }
}