package arkanoid.models;

public class Brick implements RectShape {
    private int posX;
    private int posY;
    private int width;
    private int height;
    private int hitsForDestroying;
    private int hitsForDestroyingStartVal;

    public Brick(int posX, int posY, int width, int height, int hitsForDestroying) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.hitsForDestroying = hitsForDestroying;
        this.hitsForDestroyingStartVal = this.hitsForDestroying;
    }

    @Override
    public int getPosX() {
        return posX;
    }

    @Override
    public int getPosY() {
        return posY;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public int getHitsForDestroying() {
        return hitsForDestroying;
    }

    public void setHitsForDestroying(int hitsForDestroying) {
        this.hitsForDestroying = hitsForDestroying;
    }

    public int getHitsForDestroyingStartVal() {
        return hitsForDestroyingStartVal;
    }

}
