package arkanoid.controllers;

import arkanoid.models.Ball;
import arkanoid.models.Brick;
import arkanoid.models.Game;
import arkanoid.models.Player;
import arkanoid.views.View;

import java.awt.event.KeyEvent;
import java.util.Vector;

public class MainController {
    BallController ballController;
    BrickController brickController;
    PlayerController playerController;
    GameController gameController;

    View view;

    public MainController(View view) {
        this.view = view;
        this.ballController = new BallController();
        this.brickController = new BrickController();
        this.playerController = new PlayerController(new Player(this.view.getWidth() / 2 - 40, this.view.getHeight() - 100, 80, 20));
        this.gameController = new GameController(new Game(this.playerController.getPlayer()));
    }

    public void keyTyped(KeyEvent keyEvent) {
    }

    public void keyPressed(KeyEvent keyEvent) {
        if(Character.toUpperCase(keyEvent.getKeyChar()) == 'A') {
            this.playerController.moveLeft();
        } else if(Character.toUpperCase(keyEvent.getKeyChar()) == 'D') {
            this.playerController.moveRight();
        }
        this.view.updateView();
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
