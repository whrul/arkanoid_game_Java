package arkanoid.controllers;

import arkanoid.models.*;

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
    public void addGameBonus(GameBonus gameBonus) {
        this.game.getGameBonuses().add(gameBonus);
    }
    public void destroyBrick(Brick brick) {
        this.game.getBricks().remove(brick);
    }
    public void destroyBall(Ball ball) {
        this.game.getBalls().remove(ball);
    }
    public void destroyGameBonus(GameBonus gameBonus) {
        this.game.getGameBonuses().remove(gameBonus);
    }

    public int decreaseLives() {
        this.game.setLives(this.game.getLives() - 1);
        return this.game.getLives();
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
    public int getScores() {
        return this.game.getScores();
    }
    public int getLives() {
        return this.game.getLives();
    }
    public Vector<GameBonus> getGameBonuses() {
        return this.game.getGameBonuses();
    }
    public GameStatusEnum getGameStatusEnum() {
        return this.game.getGameStatusEnum();
    }
    public int getLevel() {
        return this.game.getLevel();
    }

    public void setGameStatusEnum(GameStatusEnum gameStatusEnum) {
        this.game.setGameStatusEnum(gameStatusEnum);
    }
    public void setLevel(int level) {
        this.game.setLevel(level);
    }

    public void resetGame() {
        this.game.getBricks().clear();
        this.game.getBalls().clear();
        this.game.getGameBonuses().clear();

        this.game.setScores(GameConstants.SCORES);
        this.game.setLives(GameConstants.LIVES);
        this.game.setLevel(GameConstants.LEVEL);
    }

}
