package arkanoid.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTests {

    private int posX = 5;
    private int posY = 10;
    private int width = 15;
    private int height = 20;
    private int dirX = 25;

    private Player player = new Player(posX, posY, width, height, dirX);

    @Test
    void constructedPlayerHasProperPosX() {
        assertEquals(posX, player.getPosX());
    }

    @Test
    void constructedPlayerHasProperPosY() {
        assertEquals(posY, player.getPosY());
    }

    @Test
    void constructedPlayerHasProperWidth() {
        assertEquals(width, player.getWidth());
    }

    @Test
    void constructedPlayerHasProperHeight() {
        assertEquals(height, player.getHeight());
    }

    @Test
    void constructedPlayerHasProperDirX() {
        assertEquals(dirX, player.getDirX());
    }

    @Test
    void setPosXWorksCorrectly() {
        player.setPosX(7);
        assertEquals(7, player.getPosX());
    }

    @Test
    void setPosYWorksCorrectly() {
        player.setPosY(11);
        assertEquals(11, player.getPosY());
    }

    @Test
    void setWidthWorksCorrectly() {
        player.setWidth(13);
        assertEquals(13, player.getWidth());
    }

    @Test
    void setHeightWorksCorrectly() {
        player.setHeight(17);
        assertEquals(17, player.getHeight());
    }

    @Test
    void setDirXWorksCorrectly() {
        player.setDirX(19);
        assertEquals(19, player.getDirX());
    }

}
