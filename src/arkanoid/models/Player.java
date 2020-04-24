// Author: Walerij Hrul
//
package arkanoid.models;

public class Player {
    private int posX;
    private int posY;
    private int width;
    private int height;
    private int dirX;

    private Player() {};

//    public Player(int posX, int posY, int width, int height, int dirX) {
//        this.posX = posX;
//        this.posY = posY;
//        this.width = width;
//        this.height = height;
//        this.dirX = dirX;
//    }

    public static class Builder {

        public Player build() {
            return new Player(this);
        }

        public Builder setPosX(int posX) {
            this.posX = posX;
            return this;
        }

        public Builder setPosY(int posY) {
            this.posY = posY;
            return this;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setDirX(int dirX) {
            this.dirX = dirX;
            return this;
        }

        private int posX = 0;
        private int posY = 0;
        private int width = 1;
        private int height = 1;
        private int dirX = 0;
    }

    public static Builder builder() {
        return new Builder();
    }

    private Player(Builder builder) {
        this.posX = builder.posX;
        this.posY = builder.posY;
        this.width = builder.width;
        this.height = builder.height;
        this.dirX = builder.dirX;
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
