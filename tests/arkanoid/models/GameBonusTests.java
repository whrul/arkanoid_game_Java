package arkanoid.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameBonusTests {

    private int posX = 5;
    private int posY = 10;
    private int width = 15;
    private int height = 20;
    private BonusEnum bonusEnum = BonusEnum.MOVE_PLAYER_UP;

    GameBonus gameBonus = new GameBonus(posX, posY, width, height, bonusEnum);

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
        assertEquals(false, gameBonus.isUsed());
    }

    @Test
    void setUsedXWorksCorrectly() {
        gameBonus.setUsed(true);
        assertEquals(true, gameBonus.isUsed());
    }


}
