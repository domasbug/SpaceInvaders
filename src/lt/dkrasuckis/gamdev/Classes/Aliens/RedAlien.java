package lt.dkrasuckis.gamdev.Classes.Aliens;

import lt.dkrasuckis.gamdev.Classes.Alien;
import lt.dkrasuckis.gamdev.Classes.Bomb;

import javax.swing.*;

public class RedAlien extends Alien {
    public RedAlien(int x, int y) {
        createInstance(x, y);
    }

    public void createInstance(int x, int y) {
        this.x = x;
        this.setY(y);

        bomb = new Bomb(x, y-1);
        scoreToAdd = 10;
        attackChance = 10;

        alienImg = "src/assets/alienRed.png";
        ImageIcon ii = new ImageIcon(alienImg);

        setImage(ii.getImage());
    }

    public void move(int direction) {
        this.x += direction;
    }
}
