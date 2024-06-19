package game_models;

import javax.swing.*;
import java.awt.*;

public class Character {
    private Image image;
    private int x, y;
    private int height, width;
    private boolean isVisible;

    //Posição da criação do personagem
    public Character(int x, int y){
        this.x = x;
        this.y = y;
        isVisible = true;
    }

    //Imagem e tamanho do personagem
    public void load(String imagePath){
        ImageIcon reference = new ImageIcon(imagePath);
        image = reference.getImage();
        height = image.getHeight(null);
        width = image.getWidth(null);
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth() {
        this.width = width;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
