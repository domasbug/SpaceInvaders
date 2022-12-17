package lt.dkrasuckis.gamdev;

import lt.dkrasuckis.gamdev.Classes.*;
import lt.dkrasuckis.gamdev.Classes.Aliens.GreenAlien;
import lt.dkrasuckis.gamdev.Classes.Aliens.RedAlien;
import lt.dkrasuckis.gamdev.Classes.Aliens.WhiteAlien;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Game extends JPanel {

    private int BOARD_WIDTH;
    private int BOARD_HEIGHT;

    int BORDER_RIGHT = 60;
    int BORDER_LEFT = 5;

    int GROUND = 710;
    int BOMB_HEIGHT = 5;

    int ALIEN_HEIGHT = 32;
    int ALIEN_WIDTH = 40;
    int ALIEN_INIT_X = 100;
    int ALIEN_INIT_Y = 100;

    int GO_DOWN = 15;
    int NUMBER_OF_ALIENS_TO_DESTROY = 0;
    int CHANCE = 10;
    int DELAY = 10;
    int PLAYER_WIDTH = 60;
    int PLAYER_HEIGHT = 30;
    int PLAYER_CANNON_OFFSET = 7;

    private Dimension dimension;
    private ArrayList<Alien> aliens = new ArrayList<>();
    private Player player;
    private Shot shot = new Shot();

    private ArrayList<ArrayList<Shield>> shields = new ArrayList<>(3);

    private int direction = -1;
    private int destroyedShips = 0;

    private int playerLives = 3;
    private int score = 0;

    private boolean inGame = true;
    private String explImg = "src/assets/explosion.png";
    private String message = "Game Over";

    private Timer timer;

    private int bombsCounter = 0;

    Font gameFont = new Font("Courier New", Font.BOLD, 20); //https://alvinalexander.com/blog/post/jfc-swing/swing-faq-list-fonts-current-platform/
    Font screenFont = new Font("Courier", Font.BOLD, 14);

    public Game(int BOARD_WIDTH, int BOARD_HEIGHT) {
        this.BOARD_WIDTH = BOARD_WIDTH;
        this.BOARD_HEIGHT = BOARD_HEIGHT;

        addKeyListener(new TAdapter());
        setFocusable(true);
        dimension = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
        setBackground(Color.black);

        timer = new Timer(DELAY, new GameCycle());
        timer.start();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 7; j++) {
                if(i == 0) {
                    GreenAlien alien = new GreenAlien(ALIEN_INIT_X + ALIEN_WIDTH + 70 * j,
                            ALIEN_INIT_Y + ALIEN_HEIGHT + 70 * i);
                    aliens.add(alien);
                }else if(i == 1) {
                    RedAlien alien = new RedAlien(ALIEN_INIT_X + ALIEN_WIDTH + 70 * j,
                            ALIEN_INIT_Y + ALIEN_HEIGHT + 70 * i);
                    aliens.add(alien);
                }else{
                    WhiteAlien alien = new WhiteAlien(ALIEN_INIT_X + ALIEN_WIDTH + 70 * j,
                            ALIEN_INIT_Y + ALIEN_HEIGHT + 70 * i);
                    aliens.add(alien);
                }
                NUMBER_OF_ALIENS_TO_DESTROY++;
            }
        }
        //Centravimo formule = BOARD_WIDTH/2 - (WIDTH pačio laivo)
        player = new Player(BOARD_WIDTH/2 - 60, GROUND-40);

        //Shield
        int coordX = 100;
        for(int i = 0; i < 3; i++){
            ArrayList<Shield> shield = new ArrayList<>();
            for(int j = 0; j < 20; j++){
                for(int k = 0; k < 6; k++) {
                    if( (j == 9 || j == 10 || j == 8 || j == 11 || j == 7 || j == 12) && (k == 0 || k == 1) ) {
                        continue;
                    }

                    if( (j == 0 || j == 19) && k == 5){
                        continue;
                    }
                    shield.add(new Shield(coordX + (4 * j), GROUND - (100 + k*5)));
                }
            }
            coordX += 230;
            shields.add(shield);
        }
    }

    private void update() {

        if (destroyedShips == NUMBER_OF_ALIENS_TO_DESTROY) {

            inGame = false;
            timer.stop();
            message = "Game won!";
        }

        //Player
        player.act(BOARD_WIDTH);

        //Shot
        if (shot.isVisible()) {

            int shotX = shot.getX();
            int shotY = shot.getY();

            for (Alien alien : aliens) {

                int alienX = alien.getX();
                int alienY = alien.getY();

                if (alien.isVisible() && shot.isVisible()) {
                    if (shotX >= (alienX)
                            && shotX <= (alienX + ALIEN_WIDTH)
                            && shotY >= (alienY)
                            && shotY <= (alienY + ALIEN_HEIGHT)) {

                        ImageIcon ii = new ImageIcon(explImg);
                        alien.setImage(ii.getImage());
                        alien.setDying(true);
                        score += alien.getScoreToAdd();
                        destroyedShips++;
                        shot.die();
                    }
                }
            }

            for (ArrayList<Shield> a : shields) {
                for (Shield s : a) {
                    if (!s.isDying() && shot.isVisible()) {
                        if (shotX >= (s.getX())
                                && shotX <= (s.getX() + 4)
                                && shotY >= (s.getY())
                                && shotY <= (s.getY() + 4)) {
                            s.setDying(true);
                            shot.die();
                            score += 5;
                        }
                    }
                }
            }

            int y = shot.getY();
            y -= 4;

            if (y < 0) {
                shot.die();
            } else {
                shot.setY(y);
            }
        }

        //Aliens
        for (Alien alien : aliens) {

            int x = alien.getX();

            if (x >= BOARD_WIDTH - BORDER_RIGHT && direction != -1) {
                //TODO: Pridėti Random direction
                direction = -1;

                Iterator<Alien> i1 = aliens.iterator();

                while (i1.hasNext()) {

                    Alien a2 = i1.next();
                    a2.setY(a2.getY() + GO_DOWN);
                }
            }

            if (x <= BORDER_LEFT && direction != 1) {

                direction = 1;

                Iterator<Alien> i2 = aliens.iterator();

                while (i2.hasNext()) {

                    Alien a = i2.next();
                    a.setY(a.getY() + GO_DOWN);
                }
            }
        }

        Iterator<Alien> it = aliens.iterator();

        while (it.hasNext()) {

            Alien alien = it.next();

            if (alien.isVisible()) {

                int y = alien.getY();

                if (y > (GROUND - 30) - ALIEN_HEIGHT) {
                    inGame = false;
                    message = "Invasion!";
                }

                alien.move(direction);
            }
        }

        //Bombs
        Random generator = new Random();

        for (Alien alien : aliens) {

            int shot = generator.nextInt(100);
            Bomb bomb = alien.getBomb();

            if (shot < alien.getAttackChance() && alien.isVisible() && bomb.isDestroyed() && bombsCounter < 6) {
                bombsCounter++;
                bomb.setDestroyed(false);
                bomb.setX(alien.getX());
                bomb.setY(alien.getY());
            }

            int bombX = bomb.getX();
            int bombY = bomb.getY();
            int playerX = player.getX();
            int playerY = player.getY();

            if (player.isVisible() && !bomb.isDestroyed()) {

                if (bombX >= (playerX)
                        && bombX <= (playerX + PLAYER_WIDTH)
                        && bombY >= (playerY)
                        && bombY <= (playerY + PLAYER_HEIGHT)) {

                    if(playerLives > 1){
                        playerLives--;
                        if(score > 100){
                            score -= 100;
                        }
                        bomb.setDestroyed(true);
                    }else{
                        ImageIcon ii = new ImageIcon(explImg);
                        player.setImage(ii.getImage());
                        player.setDying(true);
                        bomb.setDestroyed(true);
                    }
                }
            }

            if (!bomb.isDestroyed()) {

                bomb.setY(bomb.getY() + 1);

                if (bomb.getY() >= GROUND - BOMB_HEIGHT) {

                    bomb.setDestroyed(true);
                    bombsCounter--;
                }

                for (ArrayList<Shield> a : shields) {
                    for (Shield s : a) {
                        if (!s.isDying() && !bomb.isDestroyed()) {
                            if (bomb.getX() >= (s.getX())
                                    && bomb.getX() <= (s.getX() + 4)
                                    && bomb.getY() >= (s.getY())
                                    && bomb.getY() <= (s.getY() + 4)) {
                                s.setDying(true);
                                bomb.setDestroyed(true);
                                bombsCounter--;
                            }
                        }
                    }
                }
            }
        }



    }

    /*private void restart(){

    }*/

    private void drawPlayer(Graphics g) {
        if (player.isVisible()) {
            g.drawImage(player.getImage(), player.getX(), player.getY(), this);
        }

        if (player.isDying()) {
            player.die();
            inGame = false;
        }
    }

    private void drawAliens(Graphics g) {
        for (Alien alien : aliens) {
            if (alien.isVisible()) {
                g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
            }

            if (alien.isDying()) {
                alien.die();
            }
        }
    }

    private void drawShot(Graphics g) {
        if (shot.isVisible()) {
            g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
        }
    }

    private void drawBombing(Graphics g) {
        for (Alien a : aliens) {
            Bomb b = a.getBomb();
            if (!b.isDestroyed()) {
                g.drawImage(b.getImage(), b.getX(), b.getY(), this);
            }
        }
    }

    private void drawShield(Graphics g) {
        for (ArrayList<Shield> a : shields) {
            for (Shield s : a) {
                if (!s.isDying()) {
                    g.drawImage(s.getImage(), s.getX(), s.getY(), this);
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
        g.setColor(Color.green);

        if (inGame) {
            //Border
            g.drawLine(0, GROUND, BOARD_WIDTH, GROUND);
            g.drawLine(0, 0, 0, BOARD_HEIGHT);

            g.drawLine(0, 1, BOARD_WIDTH, 1);
            g.drawLine(BOARD_WIDTH-17, 0, BOARD_WIDTH-17, BOARD_HEIGHT);

            //Health
            g.setColor(Color.white);
            g.setFont(gameFont);
            g.drawString("LIVES", (BOARD_WIDTH-30)-((PLAYER_WIDTH+20)*2), 30);

            for(int i = 1; i <= 3; i++) {
                g.drawImage(new ImageIcon("src/assets/playerPlaceholder.png").getImage(), (BOARD_WIDTH - 30) - ((PLAYER_WIDTH + 20) * i), 40, this);
            }

            for(int i = 1; i <= playerLives; i++){
                g.drawImage(player.getImage(), (BOARD_WIDTH-30)-((PLAYER_WIDTH+20)*i), 40, this);
            }

            //Score
            FontMetrics fontMetrics = this.getFontMetrics(gameFont);
            g.drawString("SCORE:", 60, 30);
            g.setColor(Color.green);
            g.drawString(""+score, 40+fontMetrics.stringWidth(message), 30);

            //Objects
            drawAliens(g);
            drawPlayer(g);
            drawShot(g);
            drawBombing(g);
            drawShield(g);

        } else {
            if (timer.isRunning()) {
                timer.stop();
            }

            overScreen(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void overScreen(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);

        g.setColor(new Color(0, 32, 48));
        g.fillRect(50, BOARD_WIDTH / 2 - 30, BOARD_WIDTH - 100, 80);
        g.setColor(Color.white);
        g.drawRect(50, BOARD_WIDTH / 2 - 30, BOARD_WIDTH - 100, 80);

        FontMetrics fontMetrics = this.getFontMetrics(screenFont);
        g.setColor(Color.white);
        g.setFont(screenFont);
        g.drawString(message, (BOARD_WIDTH - fontMetrics.stringWidth(message)) / 2, BOARD_WIDTH / 2);
    }

    private void doGameCycle() {
        update();
        repaint();
    }

    private class GameCycle implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            doGameCycle();
        }
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            player.keyPressed(e);

            int x = player.getX();
            int y = player.getY();

            int key = e.getKeyCode();

            if (key == KeyEvent.VK_SPACE) {
                if (inGame) {
                    if (!shot.isVisible()) {
                        shot = new Shot(x+(PLAYER_WIDTH/2)-PLAYER_CANNON_OFFSET, y);
                    }
                }
            }
        }
    }

    public ArrayList<Alien> getAliens() {
        return aliens;
    }

    public Player getPlayer() {
        return player;
    }
}


