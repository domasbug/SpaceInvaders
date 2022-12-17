package lt.dkrasuckis.gamdev.Classes;

import javax.swing.*;

public class Bomb extends GameObject implements Destructible {
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

    @Override
    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }
}
