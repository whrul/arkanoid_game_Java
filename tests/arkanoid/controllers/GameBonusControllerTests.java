package arkanoid.controllers;

import arkanoid.models.*;
import arkanoid.views.View;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameBonusControllerTests {

    private View testView = new View() {
        @Override
        public void setController(MainController mainController) {}
        @Override
        public void updateView() {}
        @Override
        public int getWidth() { return 800; }
        @Override
        public int getHeight() { return 800; }
        @Override
        public void closeView() {}
    };
    private GameBonusController gameBonusController = new GameBonusController(testView);
    private Game game = new Game(new Player(1, 1, 1, 1, 1));

    @BeforeEach
    void allowBonuses() {
        gameBonusController.allowAllBonusesForTests();
    }
    
    @Test
    void movePlayerUpBonusShouldChangePlayerYPos() {
        GameBonus gameBonus = new GameBonus(1, 1, 1, 1, BonusEnum.MOVE_PLAYER_UP);

        game.getPlayer().setPosY(100);
        gameBonusController.makeActive(gameBonus, game.getPlayer(), game.getBalls(), game.getBricks(), testView, true);
        assert(game.getPlayer().getPosY() < 100);

        game.getPlayer().setPosY(100);
        gameBonusController.makeActive(gameBonus, game.getPlayer(), game.getBalls(), game.getBricks(), testView, false);
        assert(game.getPlayer().getPosY() > 100);
    }

    @Test
    void increasePlayerSpeedBonusShouldChangeDirXOfPlayer() {
        GameBonus gameBonus = new GameBonus(1, 1, 1, 1, BonusEnum.HIGH_SPEED_PLAYER);

        game.getPlayer().setDirX(15);
        gameBonusController.makeActive(gameBonus, game.getPlayer(), game.getBalls(), game.getBricks(), testView, true);
        assert(game.getPlayer().getDirX() > 15);

        game.getPlayer().setDirX(15);
        gameBonusController.makeActive(gameBonus, game.getPlayer(), game.getBalls(), game.getBricks(), testView, false);
        assert(game.getPlayer().getDirX() < 15);
    }

    @Test
    void decreasePlayerSpeedBonusShouldChangeDirXOfPlayer() {
        GameBonus gameBonus = new GameBonus(1, 1, 1, 1, BonusEnum.LOW_SPEED_PLAYER);

        game.getPlayer().setDirX(15);
        gameBonusController.makeActive(gameBonus, game.getPlayer(), game.getBalls(), game.getBricks(), testView, true);
        assert(game.getPlayer().getDirX() < 15);

        game.getPlayer().setDirX(15);
        gameBonusController.makeActive(gameBonus, game.getPlayer(), game.getBalls(), game.getBricks(), testView, false);
        assert(game.getPlayer().getDirX() > 15);
    }

    @Test
    void increaseBallSpeedBonusShouldChangeDirXDirYOfAllBalls() {
        GameBonus gameBonus = new GameBonus(1, 1, 1, 1, BonusEnum.HIGH_SPEED_BALLS);
        game.getBalls().clear();
        Ball ball1 = new Ball(1, 1, 1, 1, 1);
        Ball ball2 = new Ball(1, 1, 1, 1, 1);
        game.getBalls().add(ball1);
        game.getBalls().add(ball2);

        for (Ball ball : game.getBalls()) {
            ball.setDirY(5);
            ball.setDirX(5);
        }
        gameBonusController.makeActive(gameBonus, game.getPlayer(), game.getBalls(), game.getBricks(), testView, true);
        for (Ball ball : game.getBalls()) {
            assert(ball.getDirX() > 5);
            assert(ball.getDirY() > 5);
        }

        for (Ball ball : game.getBalls()) {
            ball.setDirY(5);
            ball.setDirX(5);
        }
        gameBonusController.makeActive(gameBonus, game.getPlayer(), game.getBalls(), game.getBricks(), testView, false);
        for (Ball ball : game.getBalls()) {
            assert(ball.getDirX() < 5);
            assert(ball.getDirY() < 5);
        }
    }

    @Test
    void increaseBallSpeedBonusShouldChangeDirYCoefOfAllBallsByOne() {
        GameBonus gameBonus = new GameBonus(1, 1, 1, 1, BonusEnum.HIGH_SPEED_BALLS);
        game.getBalls().clear();
        Ball ball = new Ball(1, 1, 1, 1, 1);
        game.getBalls().add(ball);
        int curDirYCoef = ball.getDirYCoef();

        gameBonusController.makeActive(gameBonus, game.getPlayer(), game.getBalls(), game.getBricks(), testView, true);

        assertEquals(curDirYCoef + 1, ball.getDirYCoef());


        gameBonusController.makeActive(gameBonus, game.getPlayer(), game.getBalls(), game.getBricks(), testView, false);

        assertEquals(curDirYCoef, ball.getDirYCoef());
    }

    @Test
    void multiplyBallsBonusShouldMultiplyAmountOfBallsByTwo() {
        GameBonus gameBonus = new GameBonus(1, 1, 1, 1, BonusEnum.MULTI_BALLS);
        game.getBalls().clear();
        for (int i = 0; i < 5; ++i) {
            game.getBalls().add(new Ball(1, 1, 1, 1, 1));
        }
        int curSize = game.getBalls().size();

        gameBonusController.makeActive(gameBonus, game.getPlayer(), game.getBalls(), game.getBricks(), testView, true);

        assertEquals(curSize * 2, game.getBalls().size());
    }

    @Test
    void makePlayerLongerBonusShouldChangeWidthOfPlayer() {
        GameBonus gameBonus = new GameBonus(1, 1, 1, 1, BonusEnum.LONG_PLAYER);

        game.getPlayer().setWidth(100);
        gameBonusController.makeActive(gameBonus, game.getPlayer(), game.getBalls(), game.getBricks(), testView, true);
        assert(game.getPlayer().getWidth() > 100);

        game.getPlayer().setWidth(100);
        gameBonusController.makeActive(gameBonus, game.getPlayer(), game.getBalls(), game.getBricks(), testView, false);
        assert(game.getPlayer().getWidth() < 100);

    }

    @Test
    void makePlayerShorterBonusShouldChangeWidthOfPlayer() {
        GameBonus gameBonus = new GameBonus(1, 1, 1, 1, BonusEnum.SHORT_PLAYER);

        game.getPlayer().setWidth(100);
        gameBonusController.makeActive(gameBonus, game.getPlayer(), game.getBalls(), game.getBricks(), testView, true);
        assert(game.getPlayer().getWidth() < 100);

        game.getPlayer().setWidth(100);
        gameBonusController.makeActive(gameBonus, game.getPlayer(), game.getBalls(), game.getBricks(), testView, false);
        assert(game.getPlayer().getWidth() > 100);

    }

    @Test
    void whenBonusFuncIsCalledThenBonusShouldChangeStateToUsed() {
        GameBonus gameBonus = new GameBonus(1, 1, 1, 1, BonusEnum.SHORT_PLAYER);

        gameBonusController.makeActive(gameBonus, game.getPlayer(), game.getBalls(), game.getBricks(), testView, true);

        assertEquals(true, gameBonus.isUsed());
    }

    @Test
    void whenBonusFuncIsCalledThenBonusShouldNotChangeStateToUsedIfControllerCanNotActivateBonus() {
        gameBonusController.forbidAllBonusesForTests();

        GameBonus gameBonus = new GameBonus(1, 1, 1, 1, BonusEnum.SHORT_PLAYER);

        gameBonusController.makeActive(gameBonus, game.getPlayer(), game.getBalls(), game.getBricks(), testView, true);

        assertEquals(false, gameBonus.isUsed());
    }

}
