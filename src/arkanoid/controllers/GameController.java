package arkanoid.controllers;

import arkanoid.models.Ball;
import arkanoid.models.Brick;
import arkanoid.models.Game;
import arkanoid.models.Player;

import java.util.Vector;

public class GameController {
    private Game game;

    public GameController(Game game) {
        this.game = game;
    }

    public void addBrick(Brick brick) {
        this.game.getBricks().add(brick);
    }
    public void addBall(Ball ball) {
        this.game.getBalls().add(ball);
    }
    public void destroyBrick(Brick brick) {
        this.game.getBricks().remove(brick);
    }
    public void destroyBall(Ball ball) {
        this.game.getBalls().remove(ball);
    }
    public void decreaseLives() {
        this.game.setLives(this.game.getLives() - 1);
    }
    public void addScores(int scores) {
        this.game.setScores(this.game.getScores() + scores);
    }

    public Player getPlayer() {
        return this.game.getPlayer();
    }
    public Vector<Ball> getBalls() {
        return this.game.getBalls();
    }
    public Vector<Brick> getBricks() {
        return this.game.getBricks();
    }

}
