package arkanoid.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTests {

    private Player player = new Player();

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
        assertEquals(GameConstants.getScores(), game.getScores());
    }

    @Test
    void constructedGameHasProperLives() {
        assertEquals(GameConstants.getLives(), game.getLives());
    }

    @Test
    void constructedGameHasProperLevel() {
        assertEquals(GameConstants.getLevel(), game.getLevel());
    }

    @Test
    void constructedGameHasProperGameStatusEnum() {
        assertEquals(GameStatusEnum.GAME_IS_START, game.getGameStatusEnum());
    }

    @Test
    void setScoresWorksCorrectly() {
        game.setScores(7);
        assertEquals(7, game.getScores());
    }

    @Test
    void setLivesWorksCorrectly() {
        game.setLives(11);
        assertEquals(11, game.getLives());
    }

    @Test
    void setLevelWorksCorrectly() {
        game.setLevel(13);
        assertEquals(13, game.getLevel());
    }

    @Test
    void setGameStatusEnumWorksCorrectly() {
        game.setGameStatusEnum(GameStatusEnum.GAME_IS_PAUSE);
        assertEquals(GameStatusEnum.GAME_IS_PAUSE, game.getGameStatusEnum());
    }


}
