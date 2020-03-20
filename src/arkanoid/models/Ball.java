package arkanoid.models;

public class Ball {
    private int radius;
    private int posX;
    private int posY;
    private int dirX;
    private int dirY;

    public Ball(int radius, int posX, int posY) {
        this.radius = radius;
        this.posX = posX;
        this.posY = posY;
        this.dirX = 0;
        this.dirY = 0;
    }

    public Ball(int radius, int posX, int posY, int dirX, int dirY) {
        this.radius = radius;
        this.posX = posX;
        this.posY = posY;
        this.dirX = dirX;
        this.dirY = dirY;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getDirX() {
        return dirX;
    }

    public void setDirX(int dirX) {
        this.dirX = dirX;
    }

    public int getDirY() {
        return dirY;
    }

    public void setDirY(int dirY) {
        this.dirY = dirY;
    }

    public int getRadius() {
        return radius;
    }
}