package arkanoid.models;

public class GameBonus implements RectShape {
    private int posX;
    private int posY;
    private int width;
    private int height;
    private BonusEnum bonusEnum;
    private boolean used = false;

    public GameBonus(int posX, int posY, int width, int height, BonusEnum bonusEnum) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.bonusEnum = bonusEnum;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
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

    public BonusEnum getBonusEnum() {
        return bonusEnum;
    }

}
