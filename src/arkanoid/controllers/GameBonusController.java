package arkanoid.controllers;

import arkanoid.models.Ball;
import arkanoid.models.Brick;
import arkanoid.models.GameBonus;
import arkanoid.models.Player;
import arkanoid.views.View;

import java.util.Vector;

public class GameBonusController {
    private int numOfDiffBon = 7;
    public void makeActive(GameBonus gameBonus, Player player, Vector<Ball> balls, Vector<Brick> bricks, View view, boolean status) {
        switch(gameBonus.getBonusCode() % this.numOfDiffBon) {
            case 0:
                this.makePlayerShort(player, view, status);
                break;
            case 1:
                this.makePlayerLong(player, view, status);
                break;
            case 2:
                this.multiplyBalls(balls, status);
                break;
            case 3:
                this.increaseBallSpeed(balls, status);
                break;
            case 4:
                this.decreasePlayerSpeed(player, status);
                break;
            case 5:
                this.increasePlayerSpeed(player, status);
                break;
            case 6:
                this.movePlayerUp(player, status);
                break;
            default:
                break;
        }
        gameBonus.setUsed(true);
    }

    private void movePlayerUp(Player player, boolean status) {
        if (status) {
            player.setPosY(player.getPosY() - player.getHeight());
        } else {
            player.setPosY(player.getPosY() + player.getHeight());
        }
    }

    private void increasePlayerSpeed(Player player, boolean status) {
        if (status) {
            player.setDirX(player.getDirX() * 2);
        } else {
            this.decreasePlayerSpeed(player, true);
        }
    }

    private void decreasePlayerSpeed(Player player, boolean status) {
        if (status) {
            player.setDirX(player.getDirX() / 2);
        } else {
            this.increasePlayerSpeed(player, true);
        }
    }

    private void increaseBallSpeed(Vector<Ball> balls, boolean status) {
        for (Ball ball : balls) {
            if (status) {
                ball.setDirX(ball.getDirX() * 2);
                ball.setDirY(ball.getDirY() * 2);
            } else {
                ball.setDirX(ball.getDirX() / 2);
                ball.setDirY(ball.getDirY() / 2);
            }
        }
    }

    private void multiplyBalls(Vector<Ball> balls, boolean status) {
        if (status) {
            balls.add(new Ball(8, 600, 300, -4, -5));
        }
    }

    private void makePlayerLong(Player player, View view, boolean status) {
        if (status) {
            if (player.getWidth() * 2 < view.getWidth()) {
                player.setPosX(player.getPosX() - player.getWidth() / 2);
                player.setWidth(player.getWidth() * 2);
            }
        } else {
            this.makePlayerShort(player, view, true);
        }

    }

    private void makePlayerShort(Player player, View view, boolean status) {
        if (status) {
            if (player.getWidth() / 2 > 20) {
                player.setPosX(player.getPosX() + player.getWidth() / 2);
                player.setWidth(player.getWidth() / 2);
            }
        } else {
            this.makePlayerLong(player, view, true);
        }

    }

    public int getNumOfDiffBon() {
        return numOfDiffBon;
    }

}
