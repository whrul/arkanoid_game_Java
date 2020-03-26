package arkanoid.controllers;

import arkanoid.models.Ball;
import arkanoid.models.Brick;
import arkanoid.models.GameBonus;
import arkanoid.models.Player;
import arkanoid.views.View;

import java.util.Vector;

public class GameBonusController {
    public GameBonusController() {

    }
    public void makeActive(GameBonus gameBonus, Player player, Vector<Ball> balls, Vector<Brick> bricks, View view, boolean status) {
        switch(gameBonus.getBonusEnum()) {
            case SHORT_PLAYER:
                this.makePlayerShorter(player, status);
                break;
            case LONG_PLAYER:
                this.makePlayerLonger(player, view, status);
                break;
            case MULTI_BALLS:
                this.multiplyBalls(balls, status);
                break;
            case HIGH_SPEED_BALLS:
                this.increaseBallSpeed(balls, status);
                break;
            case LOW_SPEED_PLAYER:
                this.decreasePlayerSpeed(player, status);
                break;
            case HIGH_SPEED_PLAYER:
                this.increasePlayerSpeed(player, status);
                break;
            case MOVE_PLAYER_UP:
                this.movePlayerUp(player, status);
                break;
            default:
                break;
        }
//        System.out.println("Bonus code: " + gameBonus.getBonusCode() + " satatus: " + status);
        gameBonus.setUsed(true);
    }

    private void movePlayerUp(Player player, boolean status) {
        if (status) {
            player.setPosY(player.getPosY() - player.getHeight() * 2);
        } else {
            player.setPosY(player.getPosY() + player.getHeight() * 2);
        }

    }

    private void increasePlayerSpeed(Player player, boolean status) {
        if (status) {
            player.setDirX(player.getDirX() * 2);
        } else {
            player.setDirX(player.getDirX() / 2);
        }
    }

    private void decreasePlayerSpeed(Player player, boolean status) {
        if (status) {
            player.setDirX(player.getDirX() / 2);
        } else {
            player.setDirX(player.getDirX() * 2);
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
            int ammount = balls.size();
            for (int i = 0; i < ammount; ++i) {
                balls.add(new Ball(balls.get(i).getRadius(), balls.get(i).getPosX(), balls.get(i).getPosY(), -balls.get(i).getDirX(), balls.get(i).getDirY()));
            }
        }
    }

    private void makePlayerLonger(Player player, View view, boolean status) {
        if (status) {
            player.setPosX(player.getPosX() - player.getWidth() / 2);
            player.setWidth(player.getWidth() * 2);
        } else {
            player.setWidth(player.getWidth() / 2);
            player.setPosX(player.getPosX() + player.getWidth() / 2);
        }
    }

    private void makePlayerShorter(Player player, boolean status) {
        if (status) {
            player.setWidth(player.getWidth() / 2);
            player.setPosX(player.getPosX() + player.getWidth() / 2);
        } else {
            player.setPosX(player.getPosX() - player.getWidth() / 2);
            player.setWidth(player.getWidth() * 2);
        }
    }


}