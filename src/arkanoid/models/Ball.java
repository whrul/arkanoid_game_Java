// Author: Walerij Hrul
//
package arkanoid.models;

public class Ball implements CircleShape {
    private int radius;
    private int posX;
    private int posY;
    private int dirX;
    private int dirY;
    private int dirYCoef;


    private Ball() {};

//    public Ball(int radius, int posX, int posY, int dirX, int dirY) {
//        this.radius = radius;
//        this.posX = posX;
//        this.posY = posY;
//        this.dirX = dirX;
//        this.dirY = dirY;
//    }

    public static class Builder {

        public Ball build() {
            return new Ball(this);
        }

        public Builder setRadius(int radius) {
            this.radius = radius;
            return this;
        }

        public Builder setPosX(int posX) {
            this.posX = posX;
            return this;
        }

        public Builder setPosY(int posY) {
            this.posY = posY;
            return this;
        }

        public Builder setDirX(int dirX) {
            this.dirX = dirX;
            return this;
        }

        public Builder setDirY(int dirY) {
            this.dirY = dirY;
            return this;
        }

        public Builder setDirYCoef(int dirYCoef) {
            this.dirYCoef = dirYCoef;
            return this;
        }
        private int radius = 1;
        private int posX = 0;
        private int posY = 0;
        private int dirX = 0;
        private int dirY = 0;
        private int dirYCoef = 1;


    }

    public static Builder builder() {
        return new Builder();
    }

    private Ball(Builder builder) {
        this.radius = builder.radius;
        this.posX = builder.posX;
        this.posY = builder.posY;
        this.dirX = builder.dirX;
        this.dirY = builder.dirY;
        this.dirYCoef = builder.dirYCoef;
    }

    @Override
    public int getPosX() {
        return posX;
    }

    @Override
    public void setPosX(int posX) {
        this.posX = posX;
    }

    @Override
    public int getPosY() {
        return posY;
    }

    @Override
    public void setPosY(int posY) {
        this.posY = posY;
    }

    @Override
    public int getCenterX() {
        return this.posX + this.radius;
    }

    @Override
    public int getCenterY() {
        return this.posY + this.radius;
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

    @Override
    public int getRadius() {
        return radius;
    }

    @Override
    public int getDiameter() {
        return this.radius * 2;
    }

    public int getDirYCoef() {
        return dirYCoef;
    }

    public void setDirYCoef(int dirYCoef) {
        this.dirYCoef = dirYCoef;
    }
}
