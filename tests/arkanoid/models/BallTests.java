package arkanoid.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BallTests {

    private int radius = 5;
    private int posX = 10;
    private int posY = 15;
    private int dirX = 20;
    private int dirY = 25;
    private Ball ball = new Ball(radius, posX, posY, dirX, dirY);

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
    void constructedBallHasProperDirYCoef() {
        assertEquals(1, ball.getDirYCoef());
    }

    @Test
    void setPosXWorksCorrectly() {
        ball.setPosX(11);
        assertEquals(11, ball.getPosX());
    }

    @Test
    void setPosYWorksCorrectly() {
        ball.setPosY(13);
        assertEquals(13, ball.getPosY());
    }

    @Test
    void setDirXWorksCorrectly() {
        ball.setDirX(17);
        assertEquals(17, ball.getDirX());
    }

    @Test
    void setDirYWorksCorrectly() {
        ball.setDirY(19);
        assertEquals(19, ball.getDirY());
    }

    @Test
    void setDirYCoefWorksCorrectly() {
        int newDirYCoef = ball.getDirYCoef() + 1;

        ball.setDirYCoef(newDirYCoef);

        assertEquals(newDirYCoef, ball.getDirYCoef());
    }


    @Test
    void diameterIsEqualToDoubleRadius() {
        assertEquals(ball.getDiameter(), ball.getRadius() * 2);
    }

}
