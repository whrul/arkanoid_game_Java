package arkanoid.models;

public class GameBonus {
    private int posX;
    private int posY;
    private int width;
    private int height;
    private int bonusCode;

    public GameBonus(int posX, int posY, int width, int height, int bonusCode) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.bonusCode = bonusCode;
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

    public int getHeight() {
        return height;
    }

    public int getBonusCode() {
        return bonusCode;
    }

}
