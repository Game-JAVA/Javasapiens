package game_models;

import java.awt.event.KeyEvent;

public class Spacecraft extends Character {

    private int dx, dy;
    private final int SCREEN_WIDTH = 1024;  // Largura da tela
    private final int SCREEN_HEIGHT = 728;  // Altura da tela

    //Pegando x e y do pai
    public Spacecraft(int x, int y) {
        super(x,y);
    }


    //Dando load no personagem spacecraft
    public void load() {
        super.load("res\\spacecraft.gif");
    }

    //Movimentação da nave
    public void move() {
        int newX = getX() + dx;
        int newY = getY() + dy;

        // Verificar se a nova posição está dentro dos limites da tela
        if (newX >= 0 - (getWidth()*0.38) && newX <= SCREEN_WIDTH - (getWidth()*0.6)) {
            super.setX(newX);
        }
        if (newY >= 0 - (getHeight()*0.5) && newY <= SCREEN_HEIGHT - (getHeight()*0.75)) {
            super.setY(newY);
        }
    }

    //Reconhecer quando a tecla está pressionada
    public void keyPressed(KeyEvent tecla){
        int code = tecla.getKeyCode();

        if(code == KeyEvent.VK_UP){
            dy=-3;
        }
        if(code == KeyEvent.VK_DOWN){
            dy=3;
        }
        if(code == KeyEvent.VK_RIGHT){
            dx=3;
        }
        if(code == KeyEvent.VK_LEFT){
            dx=-3;
        }
    }
    //Reconhecer quando a tecla não está pressionada
    public void keyRelease(KeyEvent tecla){
        int code = tecla.getKeyCode();

        if(code == KeyEvent.VK_UP){
            dy=0;
        }
        if(code == KeyEvent.VK_DOWN){
            dy=0;
        }
        if(code == KeyEvent.VK_RIGHT){
            dx=0;
        }
        if(code == KeyEvent.VK_LEFT){
            dx=0;
        }
    }
}