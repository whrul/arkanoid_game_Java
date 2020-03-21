package arkanoid.controllers;

import arkanoid.models.Ball;
import arkanoid.models.Brick;
import arkanoid.models.GameBonus;
import arkanoid.models.Player;

import java.util.Vector;

public class GameBonusController {
    public void makeActive(GameBonus gameBonus, Player player, Vector<Ball> balls, Vector<Brick> bricks) {
        switch(gameBonus.getBonusCode()) {
            case 0:
                this.makePlayerShort(player);
                break;
            case 1:
                this.makePlayerLong(player);
                break;
            case 2:
                this.multiplyBalls(balls);
                break;
//             and so on
            default:
                break;
        }
    }

    private void multiplyBalls(Vector<Ball> balls) {
        balls.add(new Ball(8, 600, 300, -4, -5));
    }

    private void makePlayerLong(Player player) {
        player.setPosX(player.getPosX() - player.getWidth() / 2);
        player.setWidth(player.getWidth() * 2);
    }

    private void makePlayerShort(Player player) {
        player.setPosX(player.getPosX() + player.getWidth() / 2);
        player.setWidth(player.getWidth() / 2);
    }
}
