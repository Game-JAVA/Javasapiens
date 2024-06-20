package game_models;

import java.awt.*;

public class Shoot extends Character {

    private Image image;

    private boolean isVisible;

    private static int VELOCIDADE = -4;

    public Shoot(int x, int y) {
        super(x, y);
        isVisible = true;
    }

    public void load() {
        super.load("res\\shoot.png");
    }

    public void move(){
        super.setY(getY() + VELOCIDADE);
        if(getY() >  938){
            isVisible = false;
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }


    public static int getVELOCIDADE() {
        return VELOCIDADE;
    }

    public static void setVELOCIDADE(int VELOCIDADE) {
        Shoot.VELOCIDADE = VELOCIDADE;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
