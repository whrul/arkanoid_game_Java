// Author: Walerij Hrul
//
package arkanoid.controllers;

import arkanoid.models.*;
import arkanoid.views.View;

import java.util.Vector;

public class GameBonusController {

    private int[] maxBonusesTheSameTime;

    public GameBonusController(View view) {
        this.maxBonusesTheSameTime = new int[BonusEnum.values().length];
        this.setUp(view);
    }
    public void makeActive(GameBonus gameBonus, Player player, Vector<Ball> balls, Vector<Brick> bricks, View view, boolean status) {

        if (status && this.maxBonusesTheSameTime[gameBonus.getBonusEnum().getIntRepr()] == 0
           || !status && !gameBonus.isUsed()) {
            return;
        }

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
        if (status) {
            --this.maxBonusesTheSameTime[gameBonus.getBonusEnum().getIntRepr()];
        } else {
            ++this.maxBonusesTheSameTime[gameBonus.getBonusEnum().getIntRepr()];
        }
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
                ball.setDirYCoef(ball.getDirYCoef() + 1);
            } else {
                ball.setDirX(ball.getDirX() / 2);
                ball.setDirY(ball.getDirY() / 2);
                ball.setDirYCoef(ball.getDirYCoef() - 1);
            }
        }
    }

    private void multiplyBalls(Vector<Ball> balls, boolean status) {
        if (status) {
            int ammount = balls.size();
            for (int i = 0; i < ammount; ++i) {
//                balls.add(new Ball(balls.get(i).getRadius(), balls.get(i).getPosX(), balls.get(i).getPosY(), -balls.get(i).getDirX(), balls.get(i).getDirY()));
                balls.add(
                    Ball.builder()
                            .setRadius(balls.get(i).getRadius())
                            .setPosX(balls.get(i).getPosX())
                            .setPosY(balls.get(i).getPosY())
                            .setDirX(-balls.get(i).getDirX())
                            .setDirY(balls.get(i).getDirY())
                            .setDirYCoef(balls.get(i).getDirYCoef())
                     .build()
                );
//                balls.lastElement().setDirYCoef(balls.get(i).getDirYCoef());
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


    public void reset(View view) {
        this.setUp(view);
    }

    private void setUp(View view) {
        this.maxBonusesTheSameTime[BonusEnum.SHORT_PLAYER.getIntRepr()] = Math.max(0, this.shortPlayerBonusMaxTheSameTime());
        this.maxBonusesTheSameTime[BonusEnum.LONG_PLAYER.getIntRepr()] = Math.max(0, this.longPlayerBonusMaxTheSameTime(view));
        this.maxBonusesTheSameTime[BonusEnum.MULTI_BALLS.getIntRepr()] = Math.max(0, this.multiplyBallsBonusMaxTheSameTime());
        this.maxBonusesTheSameTime[BonusEnum.HIGH_SPEED_BALLS.getIntRepr()] = Math.max(0, this.highSpeedBallsBonusMaxTheSameTime());
        this.maxBonusesTheSameTime[BonusEnum.LOW_SPEED_PLAYER.getIntRepr()] = Math.max(0, this.lowSpeedPlayerBonusMaxTheSameTime());
        this.maxBonusesTheSameTime[BonusEnum.HIGH_SPEED_PLAYER.getIntRepr()] = Math.max(0, this.highSpeedPlayerBonusMaxTheSameTime());
        this.maxBonusesTheSameTime[BonusEnum.MOVE_PLAYER_UP.getIntRepr()] = Math.max(0, this.movePlayerUpBonusMaxTheSameTime(view));
    }

    private int shortPlayerBonusMaxTheSameTime() {
        int counter = 0;
        int playerWidth = GameConstants.PLAYER_WIDTH;
        while (playerWidth > 20) {
            playerWidth /= 2;
            ++counter;
        }
        --counter;
        return counter;
    }

    private int longPlayerBonusMaxTheSameTime(View view) {
        int counter = 0;
        int playerWidth = GameConstants.PLAYER_WIDTH;
        while (playerWidth < view.getWidth()) {
            playerWidth *= 2;
            ++counter;
        }
        --counter;
        return counter;
    }

    private int multiplyBallsBonusMaxTheSameTime() {
        return 3;
    }

    private int highSpeedBallsBonusMaxTheSameTime() {
        int counter = 0;
        int ballSpeedX = Math.abs(GameConstants.BALL_DIR_X);
        int ballSpeedY = Math.abs(GameConstants.BALL_DIR_Y);

        while (ballSpeedX < GameConstants.BALL_RADIUS * 2 && ballSpeedY < GameConstants.BALL_RADIUS * 2) {
            ballSpeedX *= 2;
            ballSpeedY *= 2;
            ++counter;
        }
        --counter;
        return counter;
    }

    private int lowSpeedPlayerBonusMaxTheSameTime() {
        int counter = 0;
        int playerSpeed = Math.abs(GameConstants.PLAYER_DIR_X);
        while (playerSpeed > 0) {
            playerSpeed /= 2;
            ++counter;
        }
        --counter;
        return counter;
    }

    private int highSpeedPlayerBonusMaxTheSameTime() {
        int counter = 0;
        int playerSpeed = Math.abs(GameConstants.PLAYER_DIR_X);
        while (playerSpeed < GameConstants.PLAYER_WIDTH / 2) {
            playerSpeed *= 2;
            ++counter;
        }
        --counter;
        return counter;
    }

    private int movePlayerUpBonusMaxTheSameTime(View view) {
        int counter = 0;
        int playerPosY = view.getHeight() - GameConstants.PLAYER_HEIGHT * 3;
        while (playerPosY > view.getHeight() / 2) {
            playerPosY -= GameConstants.PLAYER_HEIGHT * 2;
            ++counter;
        }
        --counter;
        return counter;
    }

    public void allowAllBonusesForTests() {
        for (int i = 0; i < this.maxBonusesTheSameTime.length; ++i) {
            this.maxBonusesTheSameTime[i] = 1;
        }
    }

    public void forbidAllBonusesForTests() {
        for (int i = 0; i < this.maxBonusesTheSameTime.length; ++i) {
            this.maxBonusesTheSameTime[i] = 0;
        }
    }
}