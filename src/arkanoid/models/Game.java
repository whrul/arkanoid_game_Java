package arkanoid.models;

import java.util.Vector;

public class Game {
    private Player player;
    private Vector<Brick> bricks = new Vector<Brick>();;
    private Vector<Ball> balls = new Vector<Ball>();
    private Vector<GameBonus> gameBonuses = new Vector<GameBonus>();

    private int scores = GameConstants.SCORES;
    private int lives = GameConstants.LIVES;
    private int level = GameConstants.LEVEL;

    private GameStatusEnum gameStatusEnum = GameStatusEnum.GAME_IS_START;

    public Game(Player player) {
        this.player = player;
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

    public Vector<GameBonus> getGameBonuses() {
        return this.gameBonuses;
    }

    public GameStatusEnum getGameStatusEnum() {
        return gameStatusEnum;
    }

    public void setGameStatusEnum(GameStatusEnum gameStatusEnum) {
        this.gameStatusEnum = gameStatusEnum;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
