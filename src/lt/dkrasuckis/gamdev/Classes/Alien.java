package lt.dkrasuckis.gamdev.Classes;

import javax.swing.*;

public class Alien extends Object{
    private Bomb bomb;

    public Alien(int x, int y) {
        this.x = x;
        this.y = y;

        bomb = new Bomb(x, y-1);
        //TODO: Pridėti daugiau ateivių
        String alienImg = "src/assets/alien.png";
        ImageIcon ii = new ImageIcon(alienImg);

        setImage(ii.getImage());
    }

    public void move(int direction) {
        this.x += direction;
    }

    public Bomb getBomb() {
        return bomb;
    }
}
