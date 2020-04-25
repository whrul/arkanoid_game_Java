// Author: Walerij Hrul
//
package arkanoid.controllersTests;

import arkanoid.controllers.BallController;
import arkanoid.models.Ball;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BallControllerTests {

    private Ball ball = Ball.builder().build();
    private BallController ballController = new BallController();

    @Test
    void reverseXDirChangeXDirToOppositeValue() {
        ball.setDirX(20);

        ballController.reverseXDir(ball);

        assertEquals(-20, ball.getDirX());
    }

    @Test
    void reverseYDirChangeYDirToOppositeValue() {
        ball.setDirY(25);

        ballController.reverseYDir(ball);

        assertEquals(-25, ball.getDirY());
    }

    @Test
    void  moveFuncShouldChangeXYPosByXYDir() {
        ball.setPosX(50);
        ball.setPosY(100);
        ball.setDirX(10);
        ball.setDirY(20);

        ballController.move(ball);

        assertEquals(60, ball.getPosX());
        assertEquals(120, ball.getPosY());
    }

    @Test
    void setNewCenterXWorksCorrectly() {
        int curCenterX = ball.getCenterX();

        ballController.setNewCenterX(ball, curCenterX + 15);

        assertEquals(curCenterX + 15, ball.getCenterX());
    }

    @Test
    void setNewCenterYWorksCorrectly() {
        int curCenterY = ball.getCenterY();

        ballController.setNewCenterY(ball, curCenterY + 15);

        assertEquals(curCenterY + 15, ball.getCenterY());
    }

}