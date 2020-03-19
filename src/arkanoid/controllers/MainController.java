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
        this.timer = new Timer(25, new GameCycle());
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
        this.playerController.getPlayer().setPosX(this.playerController.getPlayer().getPosX() + 2);
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
