package arkanoid.controllers;

import arkanoid.models.Brick;

public class BrickController {

    public int getDamaged(Brick brick) {
        brick.setHitsForDestroying(brick.getHitsForDestroying() - 1);
        return brick.getHitsForDestroying();
    }

}
