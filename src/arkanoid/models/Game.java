package arkanoid.models;

import java.util.Vector;

public class Game {
    private Player player;
    private Vector<Brick> bricks;
    private Vector<Ball> balls;
    private Vector<GameBonus> gameBonuses;

    private int scores;
    private int lives;
    private int level;

    private GameStatusEnum gameStatusEnum;

    public Game(Player player) {
        this.player = player;
        this.bricks = new Vector<Brick>();
        this.balls = new Vector<Ball>();
        this.gameBonuses = new Vector<GameBonus>();
        this.scores = GameConstants.getScores();
        this.lives = GameConstants.getLives();
        this.level = GameConstants.getLevel();
        this.gameStatusEnum = GameStatusEnum.GAME_IS_START;
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
