package arkanoid.models;

public class Brick {
    private int posX;
    private int posY;
    private int width;
    private int height;
    private int hitsForDestroying;

    public Brick(int posX, int posY, int width, int height, int hitsForDestroying) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.hitsForDestroying = hitsForDestroying;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getHitsForDestroying() {
        return hitsForDestroying;
    }

    public void setHitsForDestroying(int hitsForDestroying) {
        this.hitsForDestroying = hitsForDestroying;
    }
}
