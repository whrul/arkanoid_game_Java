package arkanoid.controllers;

import arkanoid.models.Ball;
import arkanoid.models.Brick;
import arkanoid.models.Game;
import arkanoid.models.Player;
import arkanoid.views.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Vector;

public class MainController {
    private BallController ballController;
    private BrickController brickController;
    private PlayerController playerController;
    private GameController gameController;

    private View view;
    private Timer timer;

    public MainController(View view) {
        this.view = view;
        this.ballController = new BallController();
        this.brickController = new BrickController();
        this.playerController = new PlayerController(new Player(this.view.getWidth() / 2 - 40, this.view.getHeight() - 100, 80, 20));
        this.gameController = new GameController(new Game(this.playerController.getPlayer()));

        this.gameController.addBall(new Ball(8, this.view.getWidth() / 2, this.view.getHeight() / 2, -1, -1));
        this.gameController.addBall(new Ball(8, this.view.getWidth() / 2, this.view.getHeight() / 2, 1, -1));
        this.gameController.addBall(new Ball(8, this.view.getWidth() / 2, this.view.getHeight() / 2, -3, 3));

        this.timer = new Timer(15, new GameCycle());
        this.timer.restart();
    }

    private class GameCycle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            doCycle();
        }
    }

    private void doCycle() {
        this.doLogics();
        this.view.updateView();
    }

    public void doLogics() {
       Vector<Ball> balls = this.gameController.getBalls();
       for (Ball ball : balls) {
           this.ballController.move(ball);
           this.checkPosition(ball);
       }
    }

    public void checkPosition(Ball ball) {
        if (ball.getPosY() < 0) {
            this.ballController.reverseYDir(ball);
            ball.setPosY(0);
        } else if (ball.getPosY() + ball.getRadius() * 2 > this.gameController.getPlayer().getPosY()) {
            this.ballController.reverseYDir(ball);
            ball.setPosY(this.gameController.getPlayer().getPosY() - ball.getRadius() * 2);
        }
        if (ball.getPosX() < 0) {
            this.ballController.reverseXDir(ball);
            ball.setPosX(0);
        } else if (ball.getPosX() + ball.getRadius() * 2 > this.view.getWidth()) {
            this.ballController.reverseXDir(ball);
            ball.setPosX(this.view.getWidth() -  ball.getRadius() * 2);
        }
    }

    public void keyTyped(KeyEvent keyEvent) {
    }

    public void keyPressed(KeyEvent keyEvent) {
        if(Character.toUpperCase(keyEvent.getKeyChar()) == 'A') {
            this.playerController.moveLeft();
        } else if(Character.toUpperCase(keyEvent.getKeyChar()) == 'D') {
            this.playerController.moveRight();
        }
//        this.view.updateView();
    }

    public void keyReleased(KeyEvent keyEvent) {
    }

    public Player getPlayer() {
        return this.gameController.getPlayer();
    }

    public Vector<Ball> getBalls() {
        return this.gameController.getBalls();
    }

    public Vector<Brick> getBricks() {
        return this.gameController.getBricks();
    }

}
