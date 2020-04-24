package arkanoid.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTests {

    private Player player = Player.builder().build();

    private Game game = new Game(player);

    @Test
    void constructedGameHasProperPlayer() {
        assertEquals(player, game.getPlayer());
    }

    @Test
    void constructedGameHasNotNullBricks() {
        assert(game.getBricks() != null);
    }

    @Test
    void constructedGameHasNotNullBalls() {
        assert(game.getBalls() != null);
    }

    @Test
    void constructedGameHasNotNullGameBonuses() {
        assert(game.getGameBonuses() != null);
    }

    @Test
    void constructedGameHasProperScores() {
        assertEquals(GameConstants.SCORES, game.getScores());
    }

    @Test
    void constructedGameHasProperLives() {
        assertEquals(GameConstants.LIVES, game.getLives());
    }

    @Test
    void constructedGameHasProperLevel() {
        assertEquals(GameConstants.LEVEL, game.getLevel());
    }

    @Test
    void constructedGameHasProperGameStatusEnum() {
        assertEquals(GameStatusEnum.GAME_IS_START, game.getGameStatusEnum());
    }

    @Test
    void setScoresWorksCorrectly() {
        int newScores = game.getScores() + 1;

        game.setScores(newScores);

        assertEquals(newScores, game.getScores());
    }

    @Test
    void setLivesWorksCorrectly() {
        int newLives = game.getLives() + 1;

        game.setLives(newLives);

        assertEquals(newLives, game.getLives());
    }

    @Test
    void setLevelWorksCorrectly() {
        int newLevel = game.getLevel() + 1;

        game.setLevel(newLevel);

        assertEquals(newLevel, game.getLevel());
    }

    @Test
    void setGameStatusEnumWorksCorrectly() {
        GameStatusEnum newGameStatusEnum = (game.getGameStatusEnum() == GameStatusEnum.GAME_IS_PAUSE) ? GameStatusEnum.GAME_IS_ON : GameStatusEnum.GAME_IS_PAUSE;

        game.setGameStatusEnum(newGameStatusEnum);

        assertEquals(newGameStatusEnum, game.getGameStatusEnum());
    }


}
