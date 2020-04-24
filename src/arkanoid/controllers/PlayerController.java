// Author: Walerij Hrul
//
package arkanoid.controllers;

import arkanoid.models.Player;

public class PlayerController {
    private Player player;

    public PlayerController(Player player) {
        this.player = player;
    }

    public void moveLeft() {
        this.player.setPosX(this.player.getPosX() - this.player.getDirX());
    }

    public void moveRight() {
        this.player.setPosX(this.player.getPosX() + this.player.getDirX());
    }

    public Player getPlayer() {
        return this.player;
    }
}
