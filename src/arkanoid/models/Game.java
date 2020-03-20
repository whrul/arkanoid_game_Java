package arkanoid.models;

import java.util.Vector;

public class Game {
    private Player player;
    private Vector<Brick> bricks;
    private Vector<Ball> balls;
    private int scores;
    private int lives;

    public Game(Player player) {
        this.player = player;
        this.bricks = new Vector<Brick>();
        this.balls = new Vector<Ball>();
        this.scores = 0;
        this.lives = 3;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Vector<Brick> getBricks() {
        return bricks;
    }


    public Vector<Ball> getBalls() {
        return balls;
    }


    public int getScores() {
        return scores;
    }

    public void setScores(int scores) {
        this.scores = scores;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }
}