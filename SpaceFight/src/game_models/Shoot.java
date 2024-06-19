package game_models;

import java.awt.*;

public class Shoot extends Character {

    private Image image;

    private static int VELOCIDADE = -4;

    public Shoot(int x, int y) {
        super(x, y);
    }

    public void load() {
        super.load("res\\shoot.png");
    }

    public void move(){
        super.setY(getY() + VELOCIDADE);
    }

    public static int getVELOCIDADE() {
        return VELOCIDADE;
    }

    public static void setVELOCIDADE(int VELOCIDADE) {
        Shoot.VELOCIDADE = VELOCIDADE;
    }


}
