package lt.dkrasuckis.gamdev.Classes;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class Player extends GameObject {
    private int width;
    private ImageIcon playerImage = new ImageIcon("src/assets/player.png");

    public Player(int START_X, int START_Y) {
        width = playerImage.getImage().getWidth(null);
        setImage(playerImage.getImage());
        setX(START_X);
        setY(START_Y);
    }

    public void act(int BOARD_WIDTH) {
        x += dx;

        if (x <= 2) {
            x = 2;
        }

        if (x >= BOARD_WIDTH - (width + 20)) {
            x = BOARD_WIDTH - (width + 20);
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = -2;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 2;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }
    }
}
