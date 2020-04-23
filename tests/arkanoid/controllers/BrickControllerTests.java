package arkanoid.controllers;

import arkanoid.models.Brick;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BrickControllerTests {

    private Brick brick = new Brick(0, 0, 0, 0, 0);
    private BrickController brickController = new BrickController();

    @Test
    void doDamageShouldDecreaseNumOfHitsForDestroyingByOne() {
        brick.setHitsForDestroying(5);

        brickController.doDamage(brick);

        assertEquals(4, brick.getHitsForDestroying());
    }

    @Test
    void getHitsForDestroyingWorksCorrectly() {
        brick.setHitsForDestroying(7);

        int hitsForDestroying = brickController.getHitsForDestroying(brick);

        assertEquals(7, hitsForDestroying);
    }

}
