package arkanoid.controllers;

import arkanoid.models.Brick;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BrickControllerTests {

    private Brick brick = new Brick(0, 0, 0, 0, 0);
    private BrickController brickController = new BrickController();

    @Test
    void getDemagedShouldDecreaseNumOfHitsForDestroyingByOne() {
        brick.setHitsForDestroying(5);

        brickController.getDamaged(brick);

        assertEquals(4, brick.getHitsForDestroying());
    }

}
