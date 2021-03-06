// Author: Walerij Hrul
//
package arkanoid.modelsTests;

import arkanoid.models.Ball;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BallTests {

    private int radius = 5;
    private int posX = 10;
    private int posY = 15;
    private int dirX = 20;
    private int dirY = 25;
    private int dirYCoef = 1;
    private Ball ball =  Ball.builder()
            .setRadius(radius)
            .setPosX(posX)
            .setPosY(posY)
            .setDirX(dirX)
            .setDirY(dirY)
            .build();

    @Test
    void constructedBallHasProperRadius() {
       assertEquals(radius, ball.getRadius());
    }

    @Test
    void constructedBallHasProperPosX() {
        assertEquals(posX, ball.getPosX());
    }

    @Test
    void constructedBallHasProperPosY() {
        assertEquals(posY, ball.getPosY());
    }

    @Test
    void constructedBallHasProperDirX() {
        assertEquals(dirX, ball.getDirX());
    }

    @Test
    void constructedBallHasProperDirY() {
        assertEquals(dirY, ball.getDirY());
    }

    @Test
    void setPosXWorksCorrectly() {
        int newPosX = ball.getPosX() + 1;

        ball.setPosX(newPosX);

        assertEquals(newPosX, ball.getPosX());
    }

    @Test
    void setPosYWorksCorrectly() {
        int newPosY = ball.getPosY() + 1;

        ball.setPosY(newPosY);

        assertEquals(newPosY, ball.getPosY());
    }

    @Test
    void setDirXWorksCorrectly() {
        int newDirX = ball.getDirX() + 1;

        ball.setDirX(newDirX);

        assertEquals(newDirX, ball.getDirX());
    }

    @Test
    void setDirYWorksCorrectly() {
        int newDirY = ball.getDirY() + 1;

        ball.setDirY(newDirY);

        assertEquals(newDirY, ball.getDirY());
    }

    @Test
    void diameterIsEqualToDoubleRadius() {
        assertEquals(ball.getDiameter(), ball.getRadius() * 2);
    }

}
