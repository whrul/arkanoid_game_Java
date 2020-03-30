package arkanoid.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BrickTests {

    private int posX = 5;
    private int posY = 10;
    private int width = 15;
    private int height = 20;
    private int hitsForDestroying = 25;

    private Brick brick = new Brick(posX, posY, width, height, hitsForDestroying);


    @Test
    void constructedBrickHasProperPosX() {
        assertEquals(posX, brick.getPosX());
    }

    @Test
    void constructedBrickHasProperPosY() {
        assertEquals(posY, brick.getPosY());
    }

    @Test
    void constructedBrickHasProperWidth() {
        assertEquals(width, brick.getWidth());
    }

    @Test
    void constructedBrickHasProperHeight() {
        assertEquals(height, brick.getHeight());
    }

    @Test
    void constructedBrickHasProperHitsForDestroying() {
        assertEquals(hitsForDestroying, brick.getHitsForDestroying());
    }

    @Test
    void setHitsForDestroyingWorksCorrectly() {
        brick.setHitsForDestroying(11);
        assertEquals(11, brick.getHitsForDestroying());
    }


}

