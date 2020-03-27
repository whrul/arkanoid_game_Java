package arkanoid.models;

public class GameConstants {
    private static int mainTimerDelay = 15;
    private static int bonusAppearTimerDelay = 3000;
    private static int bonusDestroyTimerDelay = 15000;

    private static int ballRadius = 8;
    private static int ballDirX = -5;
    private static int ballDirY = -5;

    private static int brickWidth = 100;
    private static int brickHeight = 30;
    private static int brickMargin = 10;

    private static int minSideOfBonus = 30;
    private static int maxSideOFBonus = 45;

    private static int playerWidth = 125;
    private static int playerHeight = 20;
    private static int playerDirX = 15;

    private static int lives = 10;
    private static int scores = 0;

    private static int pointsForOneHit = 100;

    public static int getMainTimerDelay() {
        return mainTimerDelay;
    }

    public static int getBonusAppearTimerDelay() {
        return bonusAppearTimerDelay;
    }

    public static int getBonusDestroyTimerDelay() {
        return bonusDestroyTimerDelay;
    }

    public static int getBallRadius() {
        return ballRadius;
    }

    public static int getBallDirX() {
        return ballDirX;
    }

    public static int getBallDirY() {
        return ballDirY;
    }

    public static int getBrickWidth() {
        return brickWidth;
    }

    public static int getBrickHeight() {
        return brickHeight;
    }

    public static int getBrickMargin() {
        return brickMargin;
    }

    public static int getMinSideOfBonus() {
        return minSideOfBonus;
    }

    public static int getMaxSideOFBonus() {
        return maxSideOFBonus;
    }

    public static int getPlayerWidth() {
        return playerWidth;
    }

    public static int getPlayerHeight() {
        return playerHeight;
    }

    public static int getPlayerDirX() {
        return playerDirX;
    }

    public static int getLives() {
        return lives;
    }

    public static int getPointsForOneHit() {
        return pointsForOneHit;
    }

    public static int getScores() {
        return scores;
    }
}
