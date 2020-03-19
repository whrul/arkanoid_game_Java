package arkanoid.controllers;

import arkanoid.models.Brick;

public class BrickController {

    public void getDamaged(Brick brick) {
        brick.setHitsForDestroying(brick.getHitsForDestroying() - 1);
    }

}
