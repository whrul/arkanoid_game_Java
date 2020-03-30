package arkanoid.models;

public class Player {
    private int posX;
    private int posY;
    private int width;
    private int height;
    private int dirX;

    public Player(){
        this(0, 0, 0, 0, 0);
    };

    public Player(int posX, int posY, int width, int height, int dirX) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.dirX = dirX;
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public int getDirX() {
        return dirX;
    }

    public void setDirX(int dirX) {
        this.dirX = dirX;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
