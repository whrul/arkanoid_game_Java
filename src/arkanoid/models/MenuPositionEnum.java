// Author: Walerij Hrul
//
package arkanoid.models;

import java.util.Arrays;

public enum MenuPositionEnum {
    CONTINUE(0), NEW_GAME(1), EXIT(2);

    private int intRepr;
    MenuPositionEnum(int intRepr) {
        this.intRepr = intRepr;
    }

    public int getIntRepr() {
        return intRepr;
    }

    public MenuPositionEnum getNext() {
        return Arrays.asList(MenuPositionEnum.values()).get(Math.min(this.intRepr + 1, MenuPositionEnum.values().length - 1 ));
    }
    public MenuPositionEnum getPrev() {
        return Arrays.asList(MenuPositionEnum.values()).get(Math.max(this.intRepr - 1, 0));
    }
}