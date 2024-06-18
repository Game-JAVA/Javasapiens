package game_models;

public class Asteroid extends Character {
    private int speed;

    public Asteroid(int x, int y){
        super(x, y);
        this.speed = (int)(Math.random() * 5) + 1; // Velocidade aleatória entre 1 e 5
    }

    public void load() {
        super.load("res\\spacecraft.gif");
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
}
