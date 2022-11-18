package lt.dkrasuckis.gamdev;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    //Konstantos
    int BOARD_WIDTH = 800;
    int BOARD_HEIGHT = 751;

    public static void main(String[] args) {
        //Nes static
        EventQueue.invokeLater(() -> {
            Main main = new Main();
            main.setVisible(true);
        });
    }

    public Main() {
        add(new Game(BOARD_WIDTH, BOARD_HEIGHT));

        setTitle("Space Invaders (Kosmoso užkariautojai)");
        setSize(BOARD_WIDTH, BOARD_HEIGHT);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
    }
}