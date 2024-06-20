package game_models;

import java.awt.*;

public class Asteroid extends Character {
    private int speed;
    private boolean isVisible;

    public Asteroid(int x, int y){
        super(x, y);
        this.speed = (int)(Math.random() * 5) + 1; // Velocidade aleatória entre 1 e 5
        isVisible = true;
    }

    public void load() {
        super.load("res\\asteroid.gif");
    }

    public Rectangle getBounds(){
        return new Rectangle(getX(),getY(), getWidth()-20, getHeight());
    }

    public void move() {
        setY(getY() + speed);
        if (getY() > 728) { // Se o asteroide sair da tela (assumindo 728 como a altura da tela)
            resetPosition();
        }
    }

    private void resetPosition() {
        setX((int)(Math.random() * 1024)); // Nova posição X aleatória
        setY(-getHeight()); // Reposicionar no topo, fora da tela
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
