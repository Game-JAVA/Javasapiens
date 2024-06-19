package game_models;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Phase extends JPanel implements ActionListener{
    private Image background;
    private Spacecraft spacecraft;
    private Timer timer;

    //Tudo o que possue a Phase
    public Phase(){
        setFocusable(true);
        setDoubleBuffered(true);

        ImageIcon reference = new ImageIcon("res\\background.jpeg");
        background = reference.getImage();

        spacecraft = new Spacecraft(360,450);
        spacecraft.load();

        addKeyListener(new TecladoAdapter());

        timer = new Timer(5,this);
        timer.start();
    }

    //Colocando grafico no background e no player para serem utilizados
    public void paint(Graphics g){
        Graphics2D graficos = (Graphics2D) g;
        graficos.drawImage(background,0,0,null);
        graficos.drawImage(spacecraft.getImage(), spacecraft.getX(), spacecraft.getY(), this);

        g.dispose();

    }

    //Atualiza a localização do player
    @Override
    public void actionPerformed(ActionEvent e) {
        spacecraft.move();
        repaint();
    }

    //Teclado pressionado
    private class TecladoAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            spacecraft.keyPressed(e);
        }

        //Teclado não pressionado
        @Override
        public void keyReleased(KeyEvent e){
            spacecraft.keyRelease(e);
        }
    }
}
