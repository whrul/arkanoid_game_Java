package arkanoid.controllers;

import arkanoid.models.Ball;

public class BallController {

    public void move(Ball ball) {
        ball.setPosX(ball.getPosX() + ball.getDirX());
        ball.setPosY(ball.getPosY() + ball.getDirY());
    }

    public void reverseXDir(Ball ball) {
        ball.setDirX(-ball.getDirX());
    }

    public void reverseYDir(Ball ball) {
        ball.setDirY(-ball.getDirX());
    }

}
