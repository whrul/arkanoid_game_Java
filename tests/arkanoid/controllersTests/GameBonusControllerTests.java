// Author: Walerij Hrul
//
package arkanoid.controllersTests;

import arkanoid.controllers.GameBonusController;
import arkanoid.controllers.MainController;
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
    private Game game = new Game(Player.builder().build());

    @BeforeEach
    void allowBonuses() {
        gameBonusController.allowAllBonusesForTests();
    }
    
    @Test
    void movePlayerUpBonusShouldDecreasePlayerYPosWhenActivate() {
        GameBonus gameBonus = GameBonus.builder().setBonusEnum(BonusEnum.MOVE_PLAYER_UP).build();
        game.getPlayer().setPosY(100);

        gameBonusController.makeActive(gameBonus, game.getPlayer(), game.getBalls(), game.getBricks(), testView, true);

        assert(game.getPlayer().getPosY() < 100);
    }
    @Test
    void movePlayerUpBonusShouldIncreasePlayerYPosWhenDeactivate() {
        GameBonus gameBonus = GameBonus.builder().setBonusEnum(BonusEnum.MOVE_PLAYER_UP).setUsed(true).build();
        game.getPlayer().setPosY(100);

        gameBonusController.makeActive(gameBonus, game.getPlayer(), game.getBalls(), game.getBricks(), testView, false);

        assert(game.getPlayer().getPosY() > 100);
    }

    @Test
    void increasePlayerSpeedBonusShouldIncreaseDirXOfPlayerWhenActivate() {
        GameBonus gameBonus = GameBonus.builder().setBonusEnum(BonusEnum.HIGH_SPEED_PLAYER).build();
        game.getPlayer().setDirX(15);

        gameBonusController.makeActive(gameBonus, game.getPlayer(), game.getBalls(), game.getBricks(), testView, true);

        assert(game.getPlayer().getDirX() > 15);
    }
    @Test
    void increasePlayerSpeedBonusShouldDecreaseDirXOfPlayerWhenDeactivate() {
        GameBonus gameBonus = GameBonus.builder().setBonusEnum(BonusEnum.HIGH_SPEED_PLAYER).setUsed(true).build();
        game.getPlayer().setDirX(15);

        gameBonusController.makeActive(gameBonus, game.getPlayer(), game.getBalls(), game.getBricks(), testView, false);

        assert(game.getPlayer().getDirX() < 15);
    }

    @Test
    void decreasePlayerSpeedBonusShouldDecreaseDirXOfPlayerWhenActivate() {
        GameBonus gameBonus = GameBonus.builder().setBonusEnum(BonusEnum.LOW_SPEED_PLAYER).build();
        game.getPlayer().setDirX(15);

        gameBonusController.makeActive(gameBonus, game.getPlayer(), game.getBalls(), game.getBricks(), testView, true);

        assert(game.getPlayer().getDirX() < 15);
    }
    @Test
    void decreasePlayerSpeedBonusShouldIncreaseDirXOfPlayerWhenDeactivate() {
        GameBonus gameBonus = GameBonus.builder().setBonusEnum(BonusEnum.LOW_SPEED_PLAYER).setUsed(true).build();
        game.getPlayer().setDirX(15);

        gameBonusController.makeActive(gameBonus, game.getPlayer(), game.getBalls(), game.getBricks(), testView, false);

        assert(game.getPlayer().getDirX() > 15);
    }

    @Test
    void increaseBallSpeedBonusShouldIncreaseDirXDirYOfAllBallsWhenActivate() {
        GameBonus gameBonus = GameBonus.builder().setBonusEnum(BonusEnum.HIGH_SPEED_BALLS).build();
        game.getBalls().clear();
        game.getBalls().add(Ball.builder().setDirX(5).setDirY(5).build());
        game.getBalls().add(Ball.builder().setDirX(5).setDirY(5).build());

        gameBonusController.makeActive(gameBonus, game.getPlayer(), game.getBalls(), game.getBricks(), testView, true);

        for (Ball ball : game.getBalls()) {
            assert(ball.getDirX() > 5);
            assert(ball.getDirY() > 5);
        }
    }
    @Test
    void increaseBallSpeedBonusShouldDecreaseDirXDirYOfAllBallsWhenDeactivate() {
        GameBonus gameBonus = GameBonus.builder().setBonusEnum(BonusEnum.HIGH_SPEED_BALLS).setUsed(true).build();
        game.getBalls().clear();
        game.getBalls().add(Ball.builder().setDirX(5).setDirY(5).build());
        game.getBalls().add(Ball.builder().setDirX(5).setDirY(5).build());

        gameBonusController.makeActive(gameBonus, game.getPlayer(), game.getBalls(), game.getBricks(), testView, false);

        for (Ball ball : game.getBalls()) {
            assert(ball.getDirX() < 5);
            assert(ball.getDirY() < 5);
        }
    }

    @Test
    void increaseBallSpeedBonusShouldIncreaseDirYCoefOfAllBallsByOneWhenActivate() {
        GameBonus gameBonus = GameBonus.builder().setBonusEnum(BonusEnum.HIGH_SPEED_BALLS).build();
        game.getBalls().clear();
        game.getBalls().add(Ball.builder().setDirYCoef(2).build());

        gameBonusController.makeActive(gameBonus, game.getPlayer(), game.getBalls(), game.getBricks(), testView, true);

        assertEquals(3,  game.getBalls().lastElement().getDirYCoef());
    }
    @Test
    void increaseBallSpeedBonusShouldDecreaseDirYCoefOfAllBallsByOneWhenDeactivate() {
        GameBonus gameBonus = GameBonus.builder().setBonusEnum(BonusEnum.HIGH_SPEED_BALLS).setUsed(true).build();
        game.getBalls().clear();
        game.getBalls().add(Ball.builder().setDirYCoef(2).build());

        gameBonusController.makeActive(gameBonus, game.getPlayer(), game.getBalls(), game.getBricks(), testView, false);

        assertEquals(1, game.getBalls().lastElement().getDirYCoef());
    }

    @Test
    void multiplyBallsBonusShouldMultiplyAmountOfBallsByTwoWhenActivate() {
        GameBonus gameBonus = GameBonus.builder().setBonusEnum(BonusEnum.MULTI_BALLS).build();
        game.getBalls().clear();
        for (int i = 0; i < 5; ++i) {
            game.getBalls().add(Ball.builder().build());
        }
        int curSize = game.getBalls().size();

        gameBonusController.makeActive(gameBonus, game.getPlayer(), game.getBalls(), game.getBricks(), testView, true);

        assertEquals(curSize * 2, game.getBalls().size());
    }

    @Test
    void makePlayerLongerBonusShouldIncreaseWidthOfPlayerWhenActivate() {
        GameBonus gameBonus = GameBonus.builder().setBonusEnum(BonusEnum.LONG_PLAYER).build();
        game.getPlayer().setWidth(100);

        gameBonusController.makeActive(gameBonus, game.getPlayer(), game.getBalls(), game.getBricks(), testView, true);

        assert(game.getPlayer().getWidth() > 100);
    }
    @Test
    void makePlayerLongerBonusShouldDecreaseWidthOfPlayerWhenDeactivate() {
        GameBonus gameBonus = GameBonus.builder().setBonusEnum(BonusEnum.LONG_PLAYER).setUsed(true).build();
        game.getPlayer().setWidth(100);

        System.out.println(game.getPlayer().getWidth());

        gameBonusController.makeActive(gameBonus, game.getPlayer(), game.getBalls(), game.getBricks(), testView, false);

        System.out.println(game.getPlayer().getWidth());

        assert(game.getPlayer().getWidth() < 100);
    }


    @Test
    void makePlayerShorterBonusShouldDecreaseWidthOfPlayerWhenActivate() {
        GameBonus gameBonus = GameBonus.builder().setBonusEnum(BonusEnum.SHORT_PLAYER).build();
        game.getPlayer().setWidth(100);

        gameBonusController.makeActive(gameBonus, game.getPlayer(), game.getBalls(), game.getBricks(), testView, true);

        assert(game.getPlayer().getWidth() < 100);
    }
    @Test
    void makePlayerShorterBonusShouldIncreaseWidthOfPlayerWhenDeactivate() {
        GameBonus gameBonus = GameBonus.builder().setBonusEnum(BonusEnum.SHORT_PLAYER).setUsed(true).build();
        game.getPlayer().setWidth(100);

        gameBonusController.makeActive(gameBonus, game.getPlayer(), game.getBalls(), game.getBricks(), testView, false);

        assert(game.getPlayer().getWidth() > 100);
    }

    @Test
    void whenBonusFuncIsCalledThenBonusShouldChangeStateToUsed() {
        GameBonus gameBonus = GameBonus.builder().build();

        gameBonusController.makeActive(gameBonus, game.getPlayer(), game.getBalls(), game.getBricks(), testView, true);

        assertEquals(true, gameBonus.isUsed());
    }

    @Test
    void whenBonusFuncIsCalledThenBonusShouldNotChangeStateToUsedIfControllerCanNotActivateBonus() {
        gameBonusController.forbidAllBonusesForTests();
        GameBonus gameBonus = GameBonus.builder().build();

        gameBonusController.makeActive(gameBonus, game.getPlayer(), game.getBalls(), game.getBricks(), testView, true);

        assertEquals(false, gameBonus.isUsed());
    }

}
