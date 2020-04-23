package arkanoid.controllers;

import arkanoid.models.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameControllerTest {

    private Player player = new Player(0, 0, 0, 0, 0);
    private Game game = new Game(player);

    private GameController gameController = new GameController(game);

    @Test
    void whenAddBrickIsCalledOnControllerNumOfBricksShouldIncreaseInModel() {
        int curSize = game.getBricks().size();
        gameController.addBrick(new Brick(1, 1, 1, 1, 1));

        assertEquals(1, game.getBricks().size() - curSize);
    }

    @Test
    void whenAddBallIsCalledOnControllerNumOfBallsShouldIncreaseInModel() {
        int curSize = game.getBalls().size();
        gameController.addBall(new Ball(1, 1, 1, 1, 1));

        assertEquals(1, game.getBalls().size() - curSize);
    }

    @Test
    void whenAddGameBonusIsCalledOnControllerNumOfGameBonusesShouldIncreaseInModel() {
        int curSize = game.getGameBonuses().size();
        gameController.addGameBonus(new GameBonus(1, 1, 1, 1, BonusEnum.MOVE_PLAYER_UP));

        assertEquals(1, game.getGameBonuses().size() - curSize);
    }

    @Test
    void whenDestroyBrickIsCalledOnControllerNumOfBricksShouldDecreaseInModel() {
        Brick brick = new Brick(1, 1, 1, 1, 1);
        gameController.addBrick(brick);
        int curSize = game.getBricks().size();
        gameController.destroyBrick(brick);

        assertEquals(-1, game.getBricks().size() - curSize);
    }

    @Test
    void whenDestroyBallIsCalledOnControllerNumOfBallsShouldDecreaseInModel() {
        Ball ball = new Ball(1, 1, 1, 1, 1);
        gameController.addBall(ball);
        int curSize = game.getBalls().size();
        gameController.destroyBall(ball);

        assertEquals(-1, game.getBalls().size() - curSize);
    }

    @Test
    void whenDestroyGameBonusIsCalledOnControllerNumOfGameBonusesShouldDecreaseInModel() {
        GameBonus gameBonus = new GameBonus(1, 1, 1, 1, BonusEnum.MOVE_PLAYER_UP);
        gameController.addGameBonus(gameBonus);
        int curSize = game.getGameBonuses().size();
        gameController.destroyGameBonus(gameBonus);

        assertEquals(-1, game.getGameBonuses().size() - curSize);
    }

    @Test
    void whenDecreaseLivesCalledOnControllerLivesShouldDecreaseInModelByOne() {
        game.setLives(5);
        gameController.decreaseLives();

        assertEquals(4, game.getLives());
    }


    @Test
    void whenAddScoresCalledOnControllerScoresShouldIncreaseInModelByArg() {
        int curValueOfScores = game.getScores();
        gameController.addScores(10);

        assertEquals(10, game.getScores() - curValueOfScores);
    }

    @Test
    void getModelAttrShouldReturnAttrOfModel() {
        assertEquals(gameController.getPlayer(), game.getPlayer());
        assertEquals(gameController.getBalls(), game.getBalls());
        assertEquals(gameController.getBricks(), game.getBricks());
        assertEquals(gameController.getScores(), game.getScores());
        assertEquals(gameController.getLives(), game.getLives());
        assertEquals(gameController.getGameBonuses(), game.getGameBonuses());
        assertEquals(gameController.getGameStatusEnum(), game.getGameStatusEnum());
        assertEquals(gameController.getLevel(), game.getLevel());
    }

    @Test
    void whenSetGameStatusEnumCalledOnControllerItShouldChangeInModel() {
        game.setGameStatusEnum(GameStatusEnum.GAME_IS_PAUSE);
        gameController.setGameStatusEnum(GameStatusEnum.GAME_IS_ON);

        assertEquals(GameStatusEnum.GAME_IS_ON, game.getGameStatusEnum());
    }

    @Test
    void whenSetLevelCalledOnControllerLevelShouldChangeInModel() {
        game.setLevel(1);
        gameController.setLevel(2);

        assertEquals(2, game.getLevel());
    }

    @Test
    void whenResetGameThenGameHasEmptyBallsBricksBonuses() {
        gameController.resetGame();

        assertEquals(0, game.getBalls().size());
        assertEquals(0, game.getBricks().size());
        assertEquals(0, game.getGameBonuses().size());
    }

    @Test
    void whenResetGameThenGameHasDefaultValuesForScoresLivesLevel() {
        gameController.resetGame();

        assertEquals(GameConstants.LEVEL, game.getLevel());
        assertEquals(GameConstants.SCORES, game.getScores());
        assertEquals(GameConstants.LIVES, game.getLives());
    }

}
