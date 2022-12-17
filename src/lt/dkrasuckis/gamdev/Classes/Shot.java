package lt.dkrasuckis.gamdev.Classes;

import javax.swing.*;

public class Shot extends GameObject {

    int H_SPACE = 6;
    int V_SPACE = 1;
    public Shot(int x, int y) {
        ImageIcon ii = new ImageIcon("src/assets/shot.png");
        setImage(ii.getImage());

        setX(x + H_SPACE);
        setY(y - V_SPACE);
    }

    public Shot() {}
}
