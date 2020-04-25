// Author: Walerij Hrul
//
package arkanoid.modelsTests;

import arkanoid.models.Brick;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BrickTests {

    private int posX = 5;
    private int posY = 10;
    private int width = 15;
    private int height = 20;
    private int hitsForDestroyingStartVal = 25;

    private Brick brick = Brick.builder()
            .setPosX(posX)
            .setPosY(posY)
            .setWidth(width)
            .setHeight(height)
            .setHitsForDestroyingStartVal(hitsForDestroyingStartVal)
            .build();


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
    void constructedBrickHasProperHitsForDestroyingStartVal() {
        assertEquals(hitsForDestroyingStartVal, brick.getHitsForDestroyingStartVal());
    }

    @Test
    void constructedBrickHasProperHitsForDestroying() {
        assertEquals(brick.getHitsForDestroyingStartVal(), brick.getHitsForDestroying());
    }

    @Test
    void setHitsForDestroyingWorksCorrectly() {
        int newHitsForDestroying = brick.getHitsForDestroying() + 1;

        brick.setHitsForDestroying(newHitsForDestroying);

        assertEquals(newHitsForDestroying, brick.getHitsForDestroying());
    }


}

