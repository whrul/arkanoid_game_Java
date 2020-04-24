package arkanoid.models;

public class GameBonus implements RectShape {
    private int posX;
    private int posY;
    private int width;
    private int height;
    private BonusEnum bonusEnum;
    private boolean used;

    private GameBonus() {};
//    public GameBonus(int posX, int posY, int width, int height, BonusEnum bonusEnum) {
//        this.posX = posX;
//        this.posY = posY;
//        this.width = width;
//        this.height = height;
//        this.bonusEnum = bonusEnum;
//    }

    public static class Builder {

        public GameBonus build() {
            return new GameBonus(this);
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

        public Builder setBonusEnum(BonusEnum bonusEnum) {
            this.bonusEnum = bonusEnum;
            return this;
        }

        public Builder setUsed(boolean used) {
            this.used = used;
            return this;
        }

        private int posX = 0;
        private int posY = 0;
        private int width = 1;
        private int height = 1;
        private BonusEnum bonusEnum = BonusEnum.MOVE_PLAYER_UP;
        private boolean used = false;
    }

    public static Builder builder() {
        return new Builder();
    }

    private GameBonus(Builder builder) {
        this.posX = builder.posX;
        this.posY = builder.posY;
        this.width = builder.width;
        this.height = builder.height;
        this.bonusEnum = builder.bonusEnum;
        this.used = builder.used;
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
