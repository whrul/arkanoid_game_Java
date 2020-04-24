package arkanoid.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTests {

    private int posX = 5;
    private int posY = 10;
    private int width = 15;
    private int height = 20;
    private int dirX = 25;

    private Player player = Player.builder()
            .setPosX(posX)
            .setPosY(posY)
            .setWidth(width)
            .setHeight(height)
            .setDirX(dirX)
        .build();

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
        int newPosX = player.getPosX() + 1;

        player.setPosX(newPosX);

        assertEquals(newPosX, player.getPosX());
    }

    @Test
    void setPosYWorksCorrectly() {
        int newPosY = player.getPosY() + 1;

        player.setPosY(newPosY);

        assertEquals(newPosY, player.getPosY());
    }

    @Test
    void setWidthWorksCorrectly() {
        int newWidth = player.getWidth() + 1;

        player.setWidth(newWidth);

        assertEquals(newWidth, player.getWidth());
    }

    @Test
    void setHeightWorksCorrectly() {
        int newHeight = player.getHeight() + 1;

        player.setHeight(newHeight);

        assertEquals(newHeight, player.getHeight());
    }

    @Test
    void setDirXWorksCorrectly() {
        int newDirX = player.getDirX() + 1;

        player.setDirX(newDirX);

        assertEquals(newDirX, player.getDirX());
    }

}
