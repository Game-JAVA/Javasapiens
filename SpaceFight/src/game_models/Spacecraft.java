package game_models;

import java.awt.event.KeyEvent;

public class Spacecraft extends Character {

    private int dx, dy;

    //Pegando x e y do pai
    public Spacecraft(int x, int y) {
        super(x,y);
    }


    //Dando load no personagem spacecraft
    public void load() {
        super.load("res\\spacecraft.gif");
    }

    //Movimentação da nave
    public void move(){
        super.setX(getX() + dx);
        super.setY(getY() + dy);
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