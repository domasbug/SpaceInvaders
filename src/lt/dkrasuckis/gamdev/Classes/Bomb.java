package lt.dkrasuckis.gamdev.Classes;

import javax.swing.*;

public class Bomb extends Object{
    private boolean destroyed;

    public Bomb(int x, int y) {
        initBomb(x, y);
    }

    private void initBomb(int x, int y) {
        setDestroyed(true);

        this.x = x;
        this.y = y;

        ImageIcon ii = new ImageIcon("src/assets/bomb.png");
        setImage(ii.getImage());
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
