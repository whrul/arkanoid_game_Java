package arkanoid.models;

public class Brick implements RectShape {
    private int posX;
    private int posY;
    private int width;
    private int height;
    private int hitsForDestroying;
    private int hitsForDestroyingStartVal;

    private Brick() {};

//    public Brick(int posX, int posY, int width, int height, int hitsForDestroying) {
//        this.posX = posX;
//        this.posY = posY;
//        this.width = width;
//        this.height = height;
//        this.hitsForDestroying = hitsForDestroying;
//        this.hitsForDestroyingStartVal = this.hitsForDestroying;
//    }

    public static class Builder {

        public Brick build() {
            return new Brick(this);
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

        public Builder setHitsForDestroyingStartVal(int hitsForDestroyingStartVal) {
            this.hitsForDestroyingStartVal = hitsForDestroyingStartVal;
            return this;
        }

        private int posX = 0;
        private int posY = 0;
        private int width = 1;
        private int height = 1;
        private int hitsForDestroyingStartVal = 1;
    }

    public static Builder builder() {
        return new Builder();
    }

    private Brick(Builder builder) {
        this.posX = builder.posX;
        this.posY = builder.posY;
        this.width = builder.width;
        this.height = builder.height;
        this.hitsForDestroyingStartVal = builder.hitsForDestroyingStartVal;
        this.hitsForDestroying = builder.hitsForDestroyingStartVal;
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
