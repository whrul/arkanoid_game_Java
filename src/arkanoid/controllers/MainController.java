package arkanoid.controllers;

import arkanoid.models.*;
import arkanoid.views.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Random;
import java.util.Vector;

public class MainController {
    private BallController ballController;
    private BrickController brickController;
    private PlayerController playerController;
    private GameController gameController;
    private GameBonusController gameBonusController;
    private boolean aPressed = false;
    private boolean dPressed = false;

    private View view;
    private Timer timer;
    private Timer addingBonusTimer;
    private Vector<GameBonusTimer> gameBonuses;

    public MainController() {
        this.ballController = new BallController();
        this.brickController = new BrickController();
        this.playerController = new PlayerController(new Player());
        this.gameController = new GameController(new Game(this.playerController.getPlayer()));
        this.gameBonusController = new GameBonusController();
        this.gameBonuses = new Vector<GameBonusTimer>();
    }

    public void setView(View view) {
        this.view = view;
        this.finishSetUp();
    }

    private void finishSetUp() {

        this.resetPlayerAttributes();

        this.gameController.addBall(new Ball(GameConstants.getBallRadius(), this.view.getWidth() / 2, this.view.getHeight() / 2, GameConstants.getBallDirX(), GameConstants.getBallDirY()));

        this.addBricks();

//        JOptionPane.showMessageDialog(null, "Let's start it");
//        Toolkit.getDefaultToolkit().beep();

        this.timer = new Timer(GameConstants.getMainTimerDelay(), new GameCycle());
        this.timer.restart();

        this.addingBonusTimer = new Timer(GameConstants.getBonusAppearTimerDelay(), new BonusCycle());
        this.addingBonusTimer.restart();
    }

    private void addBricks() {
        Random random = new Random();
        int ammountOfBricksInX = this.view.getWidth() / GameConstants.getBrickWidth();
        int ammoOfBricksInY = this.view.getHeight() / 3 * 1 / GameConstants.getBrickHeight();
        for (int i = 0 ; i < ammountOfBricksInX; ++i) {
            for (int j = 0; j < ammoOfBricksInY; ++j) {
                this.gameController.addBrick(new Brick((i + 1) * GameConstants.getBrickMargin() + i * GameConstants.getBrickWidth(), (j + 1) * GameConstants.getBrickMargin() + j * GameConstants.getBrickHeight(), GameConstants.getBrickWidth(), GameConstants.getBrickHeight(), random.nextInt(3) + 1));
            }
        }
    }

    public int getScores() {
        return this.gameController.getScores();
    }

    public int getLives() {
        return this.gameController.getLives();
    }

    public Vector<GameBonus> getGameBonuses() {
        return this.gameController.getGameBonuses();
    }

    public Vector<GameBonusTimer> getGameBonusTimers() {
        return this.gameBonuses;
    }

    private class GameCycle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            doCycle();
        }
    }
    private class BonusCycle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            doBonusStuff();
        }
    }
    private class EndOfBonusCycle implements ActionListener {
        private GameBonusTimer gameBonusTimer;

        public EndOfBonusCycle(GameBonusTimer gameBonusTimer) {
            this.gameBonusTimer = gameBonusTimer;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (!this.gameBonusTimer.gameBonus.isUsed()) {
                gameController.destroyGameBonus(this.gameBonusTimer.gameBonus);
            } else {
                gameBonusController.makeActive(this.gameBonusTimer.gameBonus, playerController.getPlayer(), gameController.getBalls(), gameController.getBricks(), view, false);
            }
            this.gameBonusTimer.timeForDestroying.stop();
            gameBonuses.remove(gameBonusTimer);
        }
    }

    private void doBonusStuff() {
        Random random = new Random();
        if (random.nextBoolean()) {
            int sizeOfSide = GameConstants.getMinSideOfBonus() + random.nextInt(GameConstants.getMaxSideOFBonus() - GameConstants.getMinSideOfBonus());
            GameBonus gameBonus = new GameBonus(random.nextInt(this.view.getWidth() - sizeOfSide), random.nextInt(this.getPlayer().getPosY() - sizeOfSide),  sizeOfSide, sizeOfSide, Arrays.asList(BonusEnum.values()).get(random.nextInt(BonusEnum.values().length)));
            this.gameController.addGameBonus(gameBonus);
            this.gameBonuses.add(new GameBonusTimer(gameBonus));
        }
    }

    private void doCycle() {
        this.doLogics();
        this.view.updateView();
    }

    private void doLogics() {
       if (this.aPressed) {
           this.playerController.moveLeft();
       }
       if (this.dPressed) {
           this.playerController.moveRight();
       }
       this.checkPlayerPosition();

       this.moveBallsAndActivateBonuses();
       this.checkForGameOver();
    }

    private void checkForGameOver() {

        if (this.gameController.getBalls().isEmpty() || this.gameController.getPlayer().getWidth() == 0 ||  this.gameController.getPlayer().getDirX() == 0) {
            this.gameController.getBalls().clear();
            for (GameBonusTimer gameBonusTimer : this.gameBonuses) {
                gameBonusTimer.timeForDestroying.stop();
            }
            this.gameController.getGameBonuses().clear();
            this.gameBonuses.clear();


            if (this.gameController.decreaseLives() == 0) {
                this.timer.stop();
            } else {
                this.resetPlayerAttributes();
                this.gameController.addBall(new Ball(GameConstants.getBallRadius(), this.view.getWidth() / 2, this.view.getHeight() / 2, GameConstants.getBallDirX(), GameConstants.getBallDirY()));
            }
        }
    }

    private void resetPlayerAttributes() {
        this.gameController.getPlayer().setWidth(GameConstants.getPlayerWidth());
        this.gameController.getPlayer().setHeight(GameConstants.getPlayerHeight());
        this.gameController.getPlayer().setDirX(GameConstants.getPlayerDirX());
        this.gameController.getPlayer().setPosX(this.view.getWidth() / 2 - this.gameController.getPlayer().getWidth() / 2);
        this.gameController.getPlayer().setPosY(this.view.getHeight() - this.gameController.getPlayer().getHeight() * 3);
    }


    private void moveBallsAndActivateBonuses() {
        Vector<Ball> balls = this.gameController.getBalls();
        for (int i = balls.size() - 1; i >= 0; --i) {
            this.ballController.move(balls.get(i));
            this.checkBallPosition(balls.get(i));
        }
    }


    private void checkBallPosition(Ball ball) {
        this.checkBallPositionWalls(ball);
        this.checkBallPositionBricks(ball);
        this.checkBallPositionGameBonuses(ball);
        this.checkBallPositionCellAndPlayer(ball);
    }

    private void checkBallPositionGameBonuses(Ball ball) {
//        Vector<GameBonus> gameBonuses = this.gameController.getGameBonuses();
        for (int i = gameBonuses.size() - 1; i >=0; --i) {
            if (!gameBonuses.get(i).gameBonus.isUsed() &&
                (circleHitsRectOnDown(ball, this.gameBonuses.get(i).gameBonus)
                || circleHitsRectOnLeft(ball, this.gameBonuses.get(i).gameBonus)
                || circleHitsRectOnRight(ball, this.gameBonuses.get(i).gameBonus)
                || circleHitsRectOnUp(ball, this.gameBonuses.get(i).gameBonus))) {
                this.gameBonusController.makeActive(this.gameBonuses.get(i).gameBonus, this.gameController.getPlayer(), this.gameController.getBalls(), this.gameController.getBricks(), this.view, true);
                this.gameController.destroyGameBonus(this.gameBonuses.get(i).gameBonus);
                this.gameBonuses.get(i).timeForDestroying.restart();
            }
        }
    }

    private void checkBallPositionWalls(Ball ball) {
        if (ball.getPosX() < 0) {
            this.ballController.reverseXDir(ball);
            ball.setPosX(0);
        } else if (ball.getPosX() + ball.getDiameter() > this.view.getWidth()) {
            this.ballController.reverseXDir(ball);
            ball.setPosX(this.view.getWidth() -  ball.getDiameter());
        }
    }
    private void checkBallPositionCellAndPlayer(Ball ball) {
        if (ball.getPosY() < 0) {
            this.ballController.reverseYDir(ball);
            ball.setPosY(0);
        } else if (ball.getPosY() + ball.getDiameter() > this.gameController.getPlayer().getPosY()) {
            if (ball.getPosX() + ball.getDiameter() < this.gameController.getPlayer().getPosX() || ball.getPosX() > this.gameController.getPlayer().getPosX() + this.gameController.getPlayer().getWidth()) {
                this.gameController.destroyBall(ball);
                return;
            }
            this.ballController.reverseYDir(ball);
            ball.setPosY(this.gameController.getPlayer().getPosY() - ball.getDiameter());
        }
    }
    private void checkBallPositionBricks(Ball ball) {
        Vector<Brick> bricks = this.gameController.getBricks();
        for (int i = bricks.size() - 1; i >= 0; --i) {
            if (this.circleHitsRectOnLeft(ball, bricks.get(i)) || this.circleHitsRectOnRight(ball, bricks.get(i))) {
                this.ballController.reverseXDir(ball);
                if (this.brickController.getDamaged(bricks.get(i)) == 0) {
                    this.gameController.addScores(GameConstants.getPointsForOneHit() * bricks.get(i).getHitsForDestroyingStartVal());
                    this.gameController.destroyBrick(bricks.get(i));
                }
            } else if (this.circleHitsRectOnUp(ball, bricks.get(i)) || this.circleHitsRectOnDown(ball, bricks.get(i))) {
                this.ballController.reverseYDir(ball);
                if (this.brickController.getDamaged(bricks.get(i)) == 0) {
                    this.gameController.addScores(GameConstants.getPointsForOneHit() * bricks.get(i).getHitsForDestroyingStartVal());
                    this.gameController.destroyBrick(bricks.get(i));
                }
            }
        }
    }

    private boolean circleHitsRectOnDown(CircleShape circleShape, RectShape rectShape) {
        if(circleShape.getPosX() + circleShape.getDiameter() > rectShape.getPosX()
                && circleShape.getPosX() + circleShape.getDiameter() < rectShape.getPosX() + rectShape.getWidth()
                && circleShape.getPosY() < rectShape.getPosY() + rectShape.getHeight()
                && circleShape.getPosY() > rectShape.getPosY()) {
            circleShape.setPosY(rectShape.getPosY() + rectShape.getHeight());
            return true;
        }
        return false;
    }

    private boolean circleHitsRectOnUp(CircleShape circleShape, RectShape rectShape) {
        if(circleShape.getPosX() + circleShape.getDiameter() > rectShape.getPosX()
                && circleShape.getPosX() + circleShape.getDiameter() < rectShape.getPosX() + rectShape.getWidth()
                && circleShape.getPosY() + circleShape.getDiameter() > rectShape.getPosY()
                && circleShape.getPosY() + circleShape.getDiameter() < rectShape.getPosY() + rectShape.getHeight()) {
            circleShape.setPosY(rectShape.getPosY() - circleShape.getDiameter());
            return true;
        }
        return false;
    }

    private boolean circleHitsRectOnRight(CircleShape circleShape, RectShape rectShape) {
        if(circleShape.getPosX() > rectShape.getPosX()
                && circleShape.getPosX() < rectShape.getPosX() + rectShape.getWidth()
                && circleShape.getPosY() > rectShape.getPosY()
                && circleShape.getPosY() + circleShape.getDiameter() < rectShape.getPosY() + rectShape.getHeight()) {
            circleShape.setPosX(rectShape.getPosX() + rectShape.getWidth());
            return true;
        }
        return false;
    }

    private boolean circleHitsRectOnLeft(CircleShape circleShape, RectShape rectShape) {
        if(circleShape.getPosX() + circleShape.getDiameter() > rectShape.getPosX()
            && circleShape.getPosX() + circleShape.getDiameter() < rectShape.getPosX() + rectShape.getWidth()
            && circleShape.getPosY() > rectShape.getPosY()
            && circleShape.getPosY() + circleShape.getDiameter() < rectShape.getPosY() + rectShape.getHeight()) {
            circleShape.setPosX(rectShape.getPosX() - circleShape.getDiameter());
            return true;
        }
        return false;
    }

    private void checkPlayerPosition() {
        if (this.gameController.getPlayer().getPosX() < 0) {
            this.gameController.getPlayer().setPosX(0);
        } else if(this.gameController.getPlayer().getPosX()  + this.gameController.getPlayer().getWidth() > this.view.getWidth()) {
            this.gameController.getPlayer().setPosX(this.view.getWidth() - this.gameController.getPlayer().getWidth());
        }
    }

    public void keyTyped(KeyEvent keyEvent) {
    }

    public void keyPressed(KeyEvent keyEvent) {
        if(Character.toUpperCase(keyEvent.getKeyChar()) == 'A') {
            this.aPressed = true;
        } else if(Character.toUpperCase(keyEvent.getKeyChar()) == 'D') {
            this.dPressed = true;
        }
    }

    public void keyReleased(KeyEvent keyEvent) {
        if(Character.toUpperCase(keyEvent.getKeyChar()) == 'A') {
            this.aPressed = false;
        } else if(Character.toUpperCase(keyEvent.getKeyChar()) == 'D') {
            this.dPressed = false;
        }
    }

    public Player getPlayer() {
        return this.gameController.getPlayer();
    }

    public Vector<Ball> getBalls() {
        return this.gameController.getBalls();
    }

    public Vector<Brick> getBricks() {
        return this.gameController.getBricks();
    }

    public class GameBonusTimer {
        private GameBonus gameBonus;
        private Timer timeForDestroying;

        public GameBonusTimer(GameBonus gameBonus) {
            this.gameBonus = gameBonus;
            this.timeForDestroying = new Timer(GameConstants.getBonusDestroyTimerDelay(), new EndOfBonusCycle(this));
            this.timeForDestroying.restart();
        }

        public boolean bonusIsActive() {
            return this.gameBonus.isUsed();
        }

        public GameBonus getGameBonus() {
            return this.gameBonus;
        }
    }

}
