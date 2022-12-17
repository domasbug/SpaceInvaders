package lt.dkrasuckis.gamdev.Classes;

import javax.swing.*;

public class Shield extends GameObject {
    int H_SPACE = 4;
    int V_SPACE = 4;

    public Shield(int x, int y) {
        ImageIcon ii = new ImageIcon("src/assets/shield.png");
        setImage(ii.getImage());

        setX(x);
        setY(y);
    }
}
