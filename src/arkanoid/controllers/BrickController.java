// Author: Walerij Hrul
//
package arkanoid.controllers;

import arkanoid.models.Brick;

public class BrickController {

    public int getHitsForDestroying(Brick brick) {
        return brick.getHitsForDestroying();
    }

    public void doDamage(Brick brick) {
        brick.setHitsForDestroying(brick.getHitsForDestroying() - 1);
    }

}
