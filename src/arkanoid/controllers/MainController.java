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
    private boolean leftPressed = false;
    private boolean rightPressed = false;

    private View view;
    private Timer timer;
    private Timer addingBonusTimer;
    private Vector<GameBonusTimer> gameBonusTimers;

    private Vector<String> menuTexts;
    private int menuPos = 1;

    public MainController() {
        this.ballController = new BallController();
        this.brickController = new BrickController();
        this.playerController = new PlayerController(new Player());
        this.gameController = new GameController(new Game(this.playerController.getPlayer()));
        this.gameBonusController = new GameBonusController();
        this.gameBonusTimers = new Vector<GameBonusTimer>();

        this.timer = new Timer(GameConstants.getMainTimerDelay(), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                doCycle();
            }
        });
        this.addingBonusTimer = new Timer(GameConstants.getBonusAppearTimerDelay(), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                doBonusStuff();
            }
        });

        this.menuTexts = new Vector<String>();
        this.menuTexts.add("CONTINUE");
        this.menuTexts.add("NEW GAME");
        this.menuTexts.add("EXIT");
        this.menuPos = 1;
    }

    public void setView(View view) {
        this.view = view;
        this.finishSetUp();
    }

    private void finishSetUp() {

        this.resetPlayerAttributes();

        this.addStartBall();
        this.addBricks();

    }

    private void addBricks() {
        Random random = new Random();
        int ammountOfBricksInX = this.view.getWidth() / GameConstants.getBrickWidth();
        int ammoOfBricksInY = this.view.getHeight() / GameConstants.getBrickHeight() / 3;
        int rightSideOfBrick = 0;
        for (int i = 0 ; i < ammountOfBricksInX; ++i) {
            for (int j = 0; j < ammoOfBricksInY; ++j) {
                if (random.nextBoolean()) {
                    rightSideOfBrick = (i + 1) * GameConstants.getBrickMargin() + i * GameConstants.getBrickWidth() + GameConstants.getBrickWidth();
                    if (rightSideOfBrick > this.view.getWidth()) {
                        continue;
                    }
                    this.gameController.addBrick(new Brick((i + 1) * GameConstants.getBrickMargin() + i * GameConstants.getBrickWidth(), (j + 1) * GameConstants.getBrickMargin() + j * GameConstants.getBrickHeight(), GameConstants.getBrickWidth(), GameConstants.getBrickHeight(), random.nextInt(this.gameController.getLevel() + 1) + 1));
                }
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
        return this.gameBonusTimers;
    }


    private void doBonusStuff() {
        Random random = new Random();
        if (random.nextBoolean()) {
            int sizeOfSide = GameConstants.getMinSideOfBonus() + random.nextInt(GameConstants.getMaxSideOFBonus() - GameConstants.getMinSideOfBonus());
            GameBonus gameBonus = new GameBonus(random.nextInt(this.view.getWidth() - sizeOfSide), random.nextInt(this.getPlayer().getPosY() - sizeOfSide),  sizeOfSide, sizeOfSide, Arrays.asList(BonusEnum.values()).get(random.nextInt(BonusEnum.values().length)));
            this.gameController.addGameBonus(gameBonus);
            this.gameBonusTimers.add(new GameBonusTimer(gameBonus));
        }
    }

    private void doCycle() {
            this.doLogics();
            this.view.updateView();
    }

    private void doLogics() {
       if (this.leftPressed) {
           this.playerController.moveLeft();
       }
       if (this.rightPressed) {
           this.playerController.moveRight();
       }
       this.checkPlayerPosition();

       this.moveBallsAndActivateBonuses();
        this.checkForLevelComplete();
        this.checkForGameOver();
    }

    private void checkForLevelComplete() {
        if (this.gameController.getBricks().isEmpty()) {
            this.stopTimers();
            this.gameController.setGameStatusEnum(GameStatusEnum.LEVEL_COMPLETE);
            this.updateImage();
            new Timer(GameConstants.getLevelCompleteScreenDuration(), new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    levelUp();
                    ((Timer)actionEvent.getSource()).stop();
                }
            }).start();

        }
    }

    private void levelUp() {
        this.stopTimers();

        this.gameController.getBricks().clear();
        this.gameController.getBalls().clear();
        this.gameController.getGameBonuses().clear();
        this.gameBonusTimers.clear();

        this.resetPlayerAttributes();


        this.gameController.setLevel(this.gameController.getLevel() + 1);
        this.addBricks();
        this.addStartBall();

        this.gameController.setGameStatusEnum(GameStatusEnum.GAME_IS_ON);

        this.runTimers();
    }

    private void checkForGameOver() {

        if (this.gameController.getBalls().isEmpty() || this.gameController.getPlayer().getWidth() == 0 ||  this.gameController.getPlayer().getDirX() == 0) {
            this.gameController.getBalls().clear();
            for (GameBonusTimer gameBonusTimer : this.gameBonusTimers) {
                gameBonusTimer.timeForDestroying.stop();
            }
            this.gameController.getGameBonuses().clear();
            this.gameBonusTimers.clear();


            if (this.gameController.decreaseLives() == 0) {
                this.stopTimers();
                this.gameController.setGameStatusEnum(GameStatusEnum.GAME_IS_OVER);
                this.updateImage();
                new Timer(GameConstants.getGameOverScreenDuration(), new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        gameController.setGameStatusEnum(GameStatusEnum.GAME_IS_START);
                        ((Timer)actionEvent.getSource()).stop();
                        updateImage();
                    }
                }).start();
            } else {
                this.resetPlayerAttributes();
                this.gameController.addBall(new Ball(GameConstants.getBallRadius(), this.view.getWidth() / 2, this.view.getHeight() / 2, GameConstants.getBallDirX(), GameConstants.getBallDirY()));
            }
        }
    }

    private void updateImage() {
        this.view.updateView();
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
        for (int i = gameBonusTimers.size() - 1; i >=0; --i) {
            if (!gameBonusTimers.get(i).gameBonus.isUsed() &&
                (circleHitsRectOnDown(ball, this.gameBonusTimers.get(i).gameBonus)
                || circleHitsRectOnLeft(ball, this.gameBonusTimers.get(i).gameBonus)
                || circleHitsRectOnRight(ball, this.gameBonusTimers.get(i).gameBonus)
                || circleHitsRectOnUp(ball, this.gameBonusTimers.get(i).gameBonus))) {
                this.gameBonusController.makeActive(this.gameBonusTimers.get(i).gameBonus, this.gameController.getPlayer(), this.gameController.getBalls(), this.gameController.getBricks(), this.view, true);
                this.gameController.destroyGameBonus(this.gameBonusTimers.get(i).gameBonus);
                this.gameBonusTimers.get(i).timeForDestroying.restart();
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
        if (circleShape.getPosX() + circleShape.getDiameter() > rectShape.getPosX()
                && circleShape.getPosX() + circleShape.getDiameter() < rectShape.getPosX() + rectShape.getWidth()
                && circleShape.getPosY() < rectShape.getPosY() + rectShape.getHeight()
                && circleShape.getPosY() > rectShape.getPosY()) {
            if (!(rectShape instanceof GameBonus)) {
                circleShape.setPosY(rectShape.getPosY() + rectShape.getHeight());
            }
            return true;
        }
        return false;
    }

    private boolean circleHitsRectOnUp(CircleShape circleShape, RectShape rectShape) {
        if (circleShape.getPosX() + circleShape.getDiameter() > rectShape.getPosX()
                && circleShape.getPosX() + circleShape.getDiameter() < rectShape.getPosX() + rectShape.getWidth()
                && circleShape.getPosY() + circleShape.getDiameter() > rectShape.getPosY()
                && circleShape.getPosY() + circleShape.getDiameter() < rectShape.getPosY() + rectShape.getHeight()) {
            if (!(rectShape instanceof GameBonus)) {
                circleShape.setPosY(rectShape.getPosY() - circleShape.getDiameter());
            }
            return true;
        }
        return false;
    }

    private boolean circleHitsRectOnRight(CircleShape circleShape, RectShape rectShape) {
        if (circleShape.getPosX() > rectShape.getPosX()
                && circleShape.getPosX() < rectShape.getPosX() + rectShape.getWidth()
                && circleShape.getPosY() > rectShape.getPosY()
                && circleShape.getPosY() + circleShape.getDiameter() < rectShape.getPosY() + rectShape.getHeight()) {
            if (!(rectShape instanceof GameBonus)) {
                circleShape.setPosX(rectShape.getPosX() + rectShape.getWidth());
            }
            return true;
        }
        return false;
    }

    private boolean circleHitsRectOnLeft(CircleShape circleShape, RectShape rectShape) {
        if (circleShape.getPosX() + circleShape.getDiameter() > rectShape.getPosX()
            && circleShape.getPosX() + circleShape.getDiameter() < rectShape.getPosX() + rectShape.getWidth()
            && circleShape.getPosY() > rectShape.getPosY()
            && circleShape.getPosY() + circleShape.getDiameter() < rectShape.getPosY() + rectShape.getHeight()) {
            if (!(rectShape instanceof GameBonus)) {
                circleShape.setPosX(rectShape.getPosX() - circleShape.getDiameter());
            }
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
        if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
            this.leftPressed = true;
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
            this.rightPressed = true;
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (this.gameController.getGameStatusEnum() == GameStatusEnum.GAME_IS_ON) {
                this.stopTimers();
                this.gameController.setGameStatusEnum(GameStatusEnum.GAME_IS_PAUSE);
                this.updateImage();
            } else if (this.gameController.getGameStatusEnum() == GameStatusEnum.GAME_IS_PAUSE) {
                this.gameController.setGameStatusEnum(GameStatusEnum.GAME_IS_ON);
                this.runTimers();
            }
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
            GameStatusEnum gameStatusEnum = this.gameController.getGameStatusEnum();
            if (gameStatusEnum == GameStatusEnum.GAME_IS_OVER || gameStatusEnum == GameStatusEnum.GAME_IS_START || gameStatusEnum == GameStatusEnum.GAME_IS_PAUSE) {
                if (this.menuPos == 1) {
                    this.restartTheGame();
                } else if (this.menuPos == 0 && gameStatusEnum == GameStatusEnum.GAME_IS_PAUSE) {
                    this.gameController.setGameStatusEnum(GameStatusEnum.GAME_IS_ON);
                    this.runTimers();
                } else if (this.menuPos == 2) {
                    this.view.closeView();
                }
            }
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
            GameStatusEnum gameStatusEnum = this.gameController.getGameStatusEnum();
            if (gameStatusEnum == GameStatusEnum.GAME_IS_OVER || gameStatusEnum == GameStatusEnum.GAME_IS_START || gameStatusEnum == GameStatusEnum.GAME_IS_PAUSE) {
                this.menuPos = Math.min(++this.menuPos, this.menuTexts.size() - 1);
                this.updateImage();
            }
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_UP) {
            GameStatusEnum gameStatusEnum = this.gameController.getGameStatusEnum();
            if (gameStatusEnum == GameStatusEnum.GAME_IS_OVER || gameStatusEnum == GameStatusEnum.GAME_IS_START || gameStatusEnum == GameStatusEnum.GAME_IS_PAUSE) {
                this.menuPos = Math.max(--this.menuPos, 0);
                this.updateImage();
            }
        }
    }

    public void keyReleased(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
            this.leftPressed = false;
        } else if(keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
            this.rightPressed = false;
        }
    }

    private void restartTheGame() {
        // reset all stuff
        this.stopTimers();

        this.gameController.resetGame();
        this.gameBonusTimers.clear();

        this.resetPlayerAttributes();

        this.addBricks();
        this.addStartBall();

        this.gameController.setGameStatusEnum(GameStatusEnum.GAME_IS_ON);

        this.runTimers();
    }

    private void addStartBall() {
        this.gameController.addBall(new Ball(GameConstants.getBallRadius(), this.view.getWidth() / 2, this.view.getHeight() / 2, GameConstants.getBallDirX(), GameConstants.getBallDirY()));
    }

    private void runTimers() {
        this.timer.start();
        this.addingBonusTimer.start();
        for (GameBonusTimer gameBonusTimer : this.gameBonusTimers) {
            gameBonusTimer.timeForDestroying.start();
        }
    }

    private void stopTimers() {
        this.timer.stop();
        this.addingBonusTimer.stop();
        for (GameBonusTimer gameBonusTimer : this.gameBonusTimers) {
            gameBonusTimer.timeForDestroying.stop();
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

    public int getLevel() {
        return this.gameController.getLevel();
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
            gameBonusTimers.remove(gameBonusTimer);
        }
    }

    public GameStatusEnum getGameStatusEnum() {
        return this.gameController.getGameStatusEnum();
    }

    public Vector<String> getMenuTexts() {
        return menuTexts;
    }

    public int getMenuPos() {
        return menuPos;
    }
}
