package game;

import game_models.Phase;

import javax.swing.*;

public class Main extends JFrame {
    //Criação da janela do jogo
    public Main(){
        add(new Phase());
        setTitle("Space Fight");
        setSize(1024,728);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        this.setResizable(false);
        setVisible(true);
    }

    public static void main (String []args){
        new Main();
    }
}