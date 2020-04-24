package arkanoid.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameBonusTests {

    private int posX = 5;
    private int posY = 10;
    private int width = 15;
    private int height = 20;
    private BonusEnum bonusEnum = BonusEnum.MOVE_PLAYER_UP;
    private boolean used = false;

    GameBonus gameBonus = GameBonus.builder()
            .setPosX(posX)
            .setPosY(posY)
            .setWidth(width)
            .setHeight(height)
            .setBonusEnum(bonusEnum)
            .setUsed(used)
        .build();

    @Test
    void constructedGameBonusHasProperPosX() {
        assertEquals(posX, gameBonus.getPosX());
    }

    @Test
    void constructedGameBonusHasProperPosY() {
        assertEquals(posY, gameBonus.getPosY());
    }

    @Test
    void constructedGameBonusHasProperWidth() {
        assertEquals(width, gameBonus.getWidth());
    }

    @Test
    void constructedGameBonusHasProperHeight() {
        assertEquals(height, gameBonus.getHeight());
    }

    @Test
    void constructedGameBonusHasProperUsedAttr() {
        assertEquals(used, gameBonus.isUsed());
    }

    @Test
    void setUsedXWorksCorrectly() {
        boolean newState = !gameBonus.isUsed();

        gameBonus.setUsed(newState);

        assertEquals(newState, gameBonus.isUsed());
    }


}
