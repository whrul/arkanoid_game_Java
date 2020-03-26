package arkanoid.models;

public enum BonusEnum {
    SHORT_PLAYER(0), LONG_PLAYER(1), MULTI_BALLS(2), HIGH_SPEED_BALLS(3), LOW_SPEED_PLAYER(4), HIGH_SPEED_PLAYER(5), MOVE_PLAYER_UP(6);

    private int intRepr;
    BonusEnum(int intRepr) {
        this.intRepr = intRepr;
    }
    public int getIntRepr() {
        return this.intRepr;
    }
}
