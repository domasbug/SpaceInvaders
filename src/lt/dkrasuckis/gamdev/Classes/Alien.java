package lt.dkrasuckis.gamdev.Classes;

public abstract class Alien extends GameObject {
    protected Bomb bomb;
    protected String alienImg;
    protected int scoreToAdd;
    protected int attackChance;

    protected abstract void createInstance(int x, int y);

    public abstract void move(int direction);

    public Bomb getBomb() {
        return bomb;
    }

    public String getAlienImg() {
        return alienImg;
    }

    public int getScoreToAdd(){
        return scoreToAdd;
    }

    public int getAttackChance(){
        return attackChance;
    }
}