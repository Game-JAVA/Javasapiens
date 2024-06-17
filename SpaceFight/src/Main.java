import javax.swing.*;

public class Main extends JFrame {
    public Main(){
        setTitle("Space Figth");
        setSize(1024,728);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        this.setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}