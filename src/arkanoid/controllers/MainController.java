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

        this.gameController.addBall(new Ball(8, this.view.getWidth() / 2, this.view.getHeight() / 2, -3, -3));
        this.gameController.addBall(new Ball(8, this.view.getWidth() / 2, this.view.getHeight() / 2, 3, -3));
        this.gameController.addBall(new Ball(8, this.view.getWidth() / 2, this.view.getHeight() / 2, -3, 3));

        this.addBricks();

        this.timer = new Timer(15, new GameCycle());
        this.timer.restart();
    }

    private void addBricks() {
        this.gameController.addBrick(new Brick(100, 100, 100, 30, 3));
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

    private void doLogics() {
       this.moveBalls();

       this.checkPlayerPosition();

       if (this.gameController.getBalls().isEmpty()) {
           this.timer.stop();
       }
    }

    private void moveBalls() {
        Vector<Ball> balls = this.gameController.getBalls();
        for (int i = balls.size() - 1; i >= 0; --i) {
            this.ballController.move(balls.get(i));
            this.checkBallPosition(balls.get(i));
        }
    }


    private void checkBallPosition(Ball ball) {
        this.checkBallPositionCellAndPlayer(ball);
        this.checkBallPositionWalls(ball);
        this.checkBallPositionBricks(ball);
    }
    private void checkBallPositionWalls(Ball ball) {
        if (ball.getPosX() < 0) {
            this.ballController.reverseXDir(ball);
            ball.setPosX(0);
        } else if (ball.getPosX() + ball.getDiameter() > this.view.getWidth()) {
            this.ballController.reverseXDir(ball);
            ball.setPosX(this.view.getWidth() -  ball.getDiameter());
        }
    }
    private void checkBallPositionCellAndPlayer(Ball ball) {
        if (ball.getPosY() < 0) {
            this.ballController.reverseYDir(ball);
            ball.setPosY(0);
        } else if (ball.getPosY() + ball.getDiameter() > this.gameController.getPlayer().getPosY()) {
            if (ball.getPosX() + ball.getDiameter() < this.gameController.getPlayer().getPosX() || ball.getPosX() > this.gameController.getPlayer().getPosX() + this.gameController.getPlayer().getWidth()) {
                this.gameController.destroyBall(ball);
                return;
            }
            this.ballController.reverseYDir(ball);
            ball.setPosY(this.gameController.getPlayer().getPosY() - ball.getDiameter());
        }
    }
    private void checkBallPositionBricks(Ball ball) {
        Vector<Brick> bricks = this.gameController.getBricks();
        for (int i = bricks.size() - 1; i >= 0; --i) {
            if (this.ballHitsBrickOnLeft(ball, bricks.get(i)) || this.ballHitsBrickOnRight(ball, bricks.get(i))) {
                this.ballController.reverseXDir(ball);
                if (this.brickController.getDamaged(bricks.get(i)) == 0) {
                    bricks.remove(bricks.get(i));
                }
            } else if (this.ballHitsBrickOnUp(ball, bricks.get(i)) || this.ballHitsBrickOnDown(ball, bricks.get(i))) {
                this.ballController.reverseYDir(ball);
                if (this.brickController.getDamaged(bricks.get(i)) == 0) {
                    bricks.remove(bricks.get(i));
                }
            }
        }
    }

    private boolean ballHitsBrickOnDown(Ball ball, Brick brick) {
        if(ball.getPosX() + ball.getDiameter() > brick.getPosX()
                && ball.getPosX() + ball.getDiameter() < brick.getPosX() + brick.getWidth()
                && ball.getPosY() < brick.getPosY() + brick.getHeight()
                && ball.getPosY() > brick.getPosY()) {
            ball.setPosY(brick.getPosY() + brick.getHeight());
            return true;
        }
        return false;
    }

    private boolean ballHitsBrickOnUp(Ball ball, Brick brick) {
        if(ball.getPosX() + ball.getDiameter() > brick.getPosX()
                && ball.getPosX() + ball.getDiameter() < brick.getPosX() + brick.getWidth()
                && ball.getPosY() + ball.getDiameter() > brick.getPosY()
                && ball.getPosY() + ball.getDiameter() < brick.getPosY() + brick.getHeight()) {
            ball.setPosY(brick.getPosY() - ball.getDiameter());
            return true;
        }
        return false;
    }

    private boolean ballHitsBrickOnRight(Ball ball, Brick brick) {
        if(ball.getPosX() > brick.getPosX()
                && ball.getPosX() < brick.getPosX() + brick.getWidth()
                && ball.getPosY() > brick.getPosY()
                && ball.getPosY() + ball.getDiameter() < brick.getPosY() + brick.getHeight()) {
            ball.setPosX(brick.getPosX() + brick.getWidth());
            return true;
        }
        return false;
    }

    private boolean ballHitsBrickOnLeft(Ball ball, Brick brick) {
        if(ball.getPosX() + ball.getDiameter() > brick.getPosX()
            && ball.getPosX() + ball.getDiameter() < brick.getPosX() + brick.getWidth()
            && ball.getPosY() > brick.getPosY()
            && ball.getPosY() + ball.getDiameter() < brick.getPosY() + brick.getHeight()) {
            ball.setPosX(brick.getPosX() - ball.getDiameter());
            return true;
        }
        return false;
    }

    private void checkPlayerPosition() {
        if (this.gameController.getPlayer().getPosX() < 0) {
            this.gameController.getPlayer().setPosX(0);
        } else if(this.gameController.getPlayer().getPosX()  + this.gameController.getPlayer().getWidth() > this.view.getWidth()) {
            this.gameController.getPlayer().setPosX(this.view.getWidth() - this.gameController.getPlayer().getWidth());
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
//        this.checkPlayerPosition();
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
