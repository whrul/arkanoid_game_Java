// Author: Walerij Hrul
//
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

    private MenuPositionEnum menuPositionEnum = MenuPositionEnum.NEW_GAME;
    private AdditionalMenuPositionEnum additionalMenuPositionEnum = AdditionalMenuPositionEnum.NEW_GAME;

    private boolean gamePause = true;

    public MainController() {
        this.ballController = new BallController();
        this.brickController = new BrickController();
        this.playerController = new PlayerController(Player.builder().build());
        this.gameController = new GameController(new Game(this.playerController.getPlayer()));
        this.gameBonusTimers = new Vector<GameBonusTimer>();

        this.timer = new Timer(GameConstants.TIMER_GAME_CYCLE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                doCycle();
            }
        });
        this.addingBonusTimer = new Timer(GameConstants.TIMER_BONUS_APPEAR, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                doBonusStuff();
            }
        });
    }

    public void setView(View view) {
        this.view = view;
        this.finishSetUp(view);
    }

    private void finishSetUp(View view) {
        this.gameBonusController = new GameBonusController(view);

        this.resetPlayerAttributes();

        this.addStartBall();
        this.addBricks();
    }

    private void addBricks() {
        Random random = new Random();
        int ammountOfBricksInX = this.view.getWidth() / GameConstants.BRICK_WIDTH;
        int ammoOfBricksInY = this.view.getHeight() / GameConstants.BRICK_HEIGHT / 3;
        int rightSideOfBrick = 0;
        for (int i = 0 ; i < ammountOfBricksInX; ++i) {
            for (int j = 0; j < ammoOfBricksInY; ++j) {
                if (random.nextBoolean()) {
                    rightSideOfBrick = (i + 1) * GameConstants.BRICK_MARGIN + i * GameConstants.BRICK_WIDTH + GameConstants.BRICK_WIDTH;
                    if (rightSideOfBrick > this.view.getWidth()) {
                        continue;
                    }
                    this.gameController.addBrick(
                            Brick.builder()
                            .setPosX((i + 1) * GameConstants.BRICK_MARGIN + i * GameConstants.BRICK_WIDTH)
                            .setPosY((j + 1) * GameConstants.BRICK_MARGIN + j * GameConstants.BRICK_HEIGHT)
                            .setWidth(GameConstants.BRICK_WIDTH)
                            .setHeight(GameConstants.BRICK_HEIGHT)
                            .setHitsForDestroyingStartVal(random.nextInt(this.gameController.getLevel() + 1) + 1)
                        .build());
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
            int sizeOfSide = GameConstants.BONUS_MIN_SIDE + random.nextInt(GameConstants.BONUS_MAX_SIDE - GameConstants.BONUS_MIN_SIDE);
            GameBonus gameBonus = GameBonus.builder()
                    .setPosX(random.nextInt(this.view.getWidth() - sizeOfSide))
                    .setPosY(random.nextInt(this.getPlayer().getPosY() - sizeOfSide))
                    .setWidth(sizeOfSide)
                    .setHeight(sizeOfSide)
                    .setBonusEnum( Arrays.asList(BonusEnum.values()).get(random.nextInt(BonusEnum.values().length)))
                    .setUsed(false)
                .build();
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

        if (this.gameController.getGameStatusEnum() == GameStatusEnum.READY_TO_START_GAME) {
            this.ballController.setNewCenterX(this.gameController.getBalls().lastElement(), this.gameController.getPlayer().getPosX() + this.gameController.getPlayer().getWidth() / 2);
        }

       if (this.getGameStatusEnum() == GameStatusEnum.GAME_IS_ON ) {
           this.moveBallsAndActivateBonuses();
           this.checkForLevelComplete();
           this.checkForGameOver();
       }

    }

    private void checkForLevelComplete() {
        if (this.gameController.getBricks().isEmpty()) {
            this.stopTimers();
            this.gameController.setGameStatusEnum(GameStatusEnum.LEVEL_COMPLETE);
            this.updateImage();
            new Timer(GameConstants.DURATION_LEVEL_COMPLETE_SCREEN, new ActionListener() {
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

        for (GameBonusTimer gameBonusTimer : this.gameBonusTimers) {
            gameBonusTimer.timeForDestroying.stop();
        }
        this.gameBonusTimers.clear();

        this.resetPlayerAttributes();

        this.gameBonusController.reset(this.view);


        this.gameController.setLevel(this.gameController.getLevel() + 1);
        this.addBricks();
        this.addStartBall();

        this.gameController.setGameStatusEnum(GameStatusEnum.READY_TO_START_GAME);
        this.timer.start();
//        this.runTimers();
    }

    private void checkForGameOver() {

        if (this.gameController.getBalls().isEmpty()) {
            for (GameBonusTimer gameBonusTimer : this.gameBonusTimers) {
                gameBonusTimer.timeForDestroying.stop();
            }
            this.gameController.getGameBonuses().clear();
            this.gameBonusTimers.clear();


            if (this.gameController.decreaseLives() == 0) {
                this.stopTimers();
                this.gameController.setGameStatusEnum(GameStatusEnum.GAME_IS_OVER);
                this.additionalMenuPositionEnum = additionalMenuPositionEnum.NEW_GAME;
                this.updateImage();
            } else {
                this.resetPlayerAttributes();
                this.gameBonusController.reset(this.view);
                this.addStartBall();
                this.stopTimers();
                this.gameController.setGameStatusEnum(GameStatusEnum.READY_TO_START_GAME);
                this.timer.start();
            }
        }
    }

    private void updateImage() {
        this.view.updateView();
    }

    private void resetPlayerAttributes() {
        this.gameController.getPlayer().setWidth(GameConstants.PLAYER_WIDTH);
        this.gameController.getPlayer().setHeight(GameConstants.PLAYER_HEIGHT);
        this.gameController.getPlayer().setDirX(GameConstants.PLAYER_DIR_X);
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
        for (int i = gameBonusTimers.size() - 1; i >=0; --i) {
            if (!gameBonusTimers.get(i).gameBonus.isUsed() &&
                (CollisionDetectorController.circleHitsRectOnDown(ball, this.gameBonusTimers.get(i).gameBonus)
                || CollisionDetectorController.circleHitsRectOnLeft(ball, this.gameBonusTimers.get(i).gameBonus)
                || CollisionDetectorController.circleHitsRectOnRight(ball, this.gameBonusTimers.get(i).gameBonus)
                || CollisionDetectorController.circleHitsRectOnUp(ball, this.gameBonusTimers.get(i).gameBonus)
                || CollisionDetectorController.circleHitsRectOnCorner(ball, this.gameBonusTimers.get(i).gameBonus))) {
                this.gameBonusController.makeActive(this.gameBonusTimers.get(i).gameBonus, this.gameController.getPlayer(), this.gameController.getBalls(), this.gameController.getBricks(), this.view, true);
                this.gameController.destroyGameBonus(this.gameBonusTimers.get(i).gameBonus);
                this.gameBonusTimers.get(i).restartTimer();
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
            if (ball.getCenterX() < this.gameController.getPlayer().getPosX() || ball.getCenterX() > this.gameController.getPlayer().getPosX() + this.gameController.getPlayer().getWidth()) {
                this.gameController.destroyBall(ball);
                return;
            }

            int ballCenterX = ball.getCenterX();
            int playerCenterX = playerController.getPlayer().getPosX() + playerController.getPlayer().getWidth() / 2;
            int diff = (ballCenterX - playerCenterX);
            double help = diff * 2D / playerController.getPlayer().getWidth() * Math.abs(GameConstants.BALL_DIR_X);
            ball.setDirX((int)help);

            this.ballController.reverseYDir(ball);
            ball.setPosY(this.gameController.getPlayer().getPosY() - ball.getDiameter());
        }
    }
    private void checkBallPositionBricks(Ball ball) {
        Vector<Brick> bricks = this.gameController.getBricks();
        boolean wasHitted = false;
        for (int i = bricks.size() - 1; i >= 0; --i) {
            wasHitted = false;
            if (CollisionDetectorController.circleHitsRectOnLeft(ball, bricks.get(i))) {
                this.ballController.reverseXDir(ball);
                ball.setPosX(bricks.get(i).getPosX() - ball.getDiameter());
                wasHitted = true;
            } else if (CollisionDetectorController.circleHitsRectOnRight(ball, bricks.get(i))) {
                this.ballController.reverseXDir(ball);
                ball.setPosX(bricks.get(i).getPosX() + bricks.get(i).getWidth());
                wasHitted = true;
            } else if (CollisionDetectorController.circleHitsRectOnUp(ball, bricks.get(i))) {
                this.ballController.reverseYDir(ball);
                ball.setPosY(bricks.get(i).getPosY() - ball.getDiameter());
                wasHitted = true;
            } else if (CollisionDetectorController.circleHitsRectOnDown(ball, bricks.get(i))) {
                this.ballController.reverseYDir(ball);
                ball.setPosY(bricks.get(i).getPosY() + bricks.get(i).getHeight());
                wasHitted = true;
            } else if (CollisionDetectorController.circleHitsRectOnLeftUpCorner(ball, bricks.get(i))) {
                if (ball.getDirY() > 0) {
                    this.ballController.reverseYDir(ball);
                }
                if (ball.getDirX() > 0) {
                    this.ballController.reverseXDir(ball);
                }
                ballController.setNewCenterX(ball, bricks.get(i).getPosX() - ball.getRadius() * (bricks.get(i).getPosX() - ball.getCenterX()) / distBetween(ball.getCenterX(), ball.getCenterY(), bricks.get(i).getPosX(), bricks.get(i).getPosY()) - 1);
                ballController.setNewCenterY(ball, bricks.get(i).getPosY() - ball.getRadius() * (bricks.get(i).getPosY() - ball.getCenterY()) / distBetween(ball.getCenterX(), ball.getCenterY(), bricks.get(i).getPosX(), bricks.get(i).getPosY()) - 1);
                wasHitted = true;
            } else if (CollisionDetectorController.circleHitsRectOnLeftDownCorner(ball, bricks.get(i))) {
                if (ball.getDirY() < 0) {
                    this.ballController.reverseYDir(ball);
                }
                if (ball.getDirX() > 0) {
                    this.ballController.reverseXDir(ball);
                }
                ballController.setNewCenterX(ball, bricks.get(i).getPosX() - ball.getRadius() * (bricks.get(i).getPosX() - ball.getCenterX()) / distBetween(ball.getCenterX(), ball.getCenterY(), bricks.get(i).getPosX(), bricks.get(i).getPosY() + bricks.get(i).getHeight()) - 1);
                ballController.setNewCenterY(ball, bricks.get(i).getPosY() + bricks.get(i).getHeight() - ball.getRadius() * (bricks.get(i).getPosY() + bricks.get(i).getHeight() - ball.getCenterY()) / distBetween(ball.getCenterX(), ball.getCenterY(), bricks.get(i).getPosX(), bricks.get(i).getPosY() + bricks.get(i).getHeight()) + 1);
                wasHitted = true;
            } else if (CollisionDetectorController.circleHitsRectOnRightUpCorner(ball, bricks.get(i))) {
                if (ball.getDirY() > 0) {
                    this.ballController.reverseYDir(ball);
                }
                if (ball.getDirX() < 0) {
                    this.ballController.reverseXDir(ball);
                }
                ballController.setNewCenterX(ball, bricks.get(i).getPosX() + bricks.get(i).getWidth() - ball.getRadius() * (bricks.get(i).getPosX() + bricks.get(i).getWidth() - ball.getCenterX()) / distBetween(ball.getCenterX(), ball.getCenterY(), bricks.get(i).getPosX() + bricks.get(i).getWidth(), bricks.get(i).getPosY()) + 1);
                ballController.setNewCenterY(ball, bricks.get(i).getPosY() - ball.getRadius() * (bricks.get(i).getPosY() - ball.getCenterY()) / distBetween(ball.getCenterX(), ball.getCenterY(), bricks.get(i).getPosX() + bricks.get(i).getWidth(), bricks.get(i).getPosY()) - 1);
                wasHitted = true;
            }  else if (CollisionDetectorController.circleHitsRectOnRightDownCorner(ball, bricks.get(i))) {
                if (ball.getDirY() < 0) {
                    this.ballController.reverseYDir(ball);
                }
                if (ball.getDirX() < 0) {
                    this.ballController.reverseXDir(ball);
                }
                ballController.setNewCenterX(ball, bricks.get(i).getPosX() + bricks.get(i).getWidth() - ball.getRadius() * (bricks.get(i).getPosX() + bricks.get(i).getWidth() - ball.getCenterX()) / distBetween(ball.getCenterX(), ball.getCenterY(), bricks.get(i).getPosX() + bricks.get(i).getWidth(), bricks.get(i).getPosY() + bricks.get(i).getHeight()) + 1);
                ballController.setNewCenterY(ball, bricks.get(i).getPosY() + bricks.get(i).getHeight() - ball.getRadius() * (bricks.get(i).getPosY() + bricks.get(i).getHeight() - ball.getCenterY()) / distBetween(ball.getCenterX(), ball.getCenterY(), bricks.get(i).getPosX() + bricks.get(i).getWidth(), bricks.get(i).getPosY() + bricks.get(i).getHeight()) + 1);
                wasHitted = true;
            }
            if (wasHitted) {
                this.brickController.doDamage(bricks.get(i));
                if (this.brickController.getHitsForDestroying(bricks.get(i)) == 0) {
                    this.gameController.addScores(GameConstants.POINTS_FOR_ONE_BRICK_HIT * bricks.get(i).getHitsForDestroyingStartVal());
                    this.gameController.destroyBrick(bricks.get(i));
                }
            }
        }
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
            this.escapeKeyPressed();
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
            this.enterKeyPressed();
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
           this.downKeyPressed();
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_UP) {
            this.upKeyPressed();
        }
    }

    private void upKeyPressed() {
        GameStatusEnum gameStatusEnum = this.gameController.getGameStatusEnum();
        if (gameStatusEnum == GameStatusEnum.GAME_IS_START || gameStatusEnum == GameStatusEnum.GAME_IS_PAUSE) {
            this.menuPositionEnum = this.menuPositionEnum.getPrev();
            this.updateImage();
        } else if (gameStatusEnum == GameStatusEnum.GAME_IS_OVER) {
            this.additionalMenuPositionEnum = AdditionalMenuPositionEnum.NEW_GAME;
            this.updateImage();
        }
    }

    private void downKeyPressed() {
        GameStatusEnum gameStatusEnum = this.gameController.getGameStatusEnum();
        if (gameStatusEnum == GameStatusEnum.GAME_IS_START || gameStatusEnum == GameStatusEnum.GAME_IS_PAUSE) {
            this.menuPositionEnum = this.menuPositionEnum.getNext();
            this.updateImage();
        } else if (gameStatusEnum == GameStatusEnum.GAME_IS_OVER) {
            this.additionalMenuPositionEnum = AdditionalMenuPositionEnum.EXIT;
            this.updateImage();
        }
    }

    private void enterKeyPressed() {
        GameStatusEnum gameStatusEnum = this.gameController.getGameStatusEnum();
        if (gameStatusEnum == GameStatusEnum.GAME_IS_START || gameStatusEnum == GameStatusEnum.GAME_IS_PAUSE) {
            if (this.menuPositionEnum == MenuPositionEnum.NEW_GAME) {
                this.restartTheGame();
            } else if (this.menuPositionEnum == MenuPositionEnum.CONTINUE && gameStatusEnum == GameStatusEnum.GAME_IS_PAUSE) {
                this.gameController.setGameStatusEnum(GameStatusEnum.GAME_IS_ON);
                this.runTimers();
            } else if (this.menuPositionEnum == MenuPositionEnum.EXIT) {
                this.view.closeView();
            }
        } else if (gameStatusEnum == GameStatusEnum.GAME_IS_OVER) {
            if (this.additionalMenuPositionEnum == AdditionalMenuPositionEnum.NEW_GAME) {
                this.restartTheGame();
            } else  if (this.additionalMenuPositionEnum == AdditionalMenuPositionEnum.EXIT) {
                this.view.closeView();
            }
        } else if (gameStatusEnum == GameStatusEnum.READY_TO_START_GAME) {
            this.gameController.setGameStatusEnum(GameStatusEnum.GAME_IS_ON);
            this.runTimers();
        }
    }

    private void escapeKeyPressed() {
        if (this.gameController.getGameStatusEnum() == GameStatusEnum.GAME_IS_ON) {
            this.stopTimers();
            this.gameController.setGameStatusEnum(GameStatusEnum.GAME_IS_PAUSE);
            this.updateImage();
        } else if (this.gameController.getGameStatusEnum() == GameStatusEnum.GAME_IS_PAUSE) {
            this.gameController.setGameStatusEnum(GameStatusEnum.GAME_IS_ON);
            this.runTimers();
        } else if (this.gameController.getGameStatusEnum() == GameStatusEnum.READY_TO_START_GAME) {
            this.gameController.setGameStatusEnum(GameStatusEnum.GAME_IS_ON);
            this.runTimers();
            this.escapeKeyPressed();
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

        for (GameBonusTimer gameBonusTimer : this.gameBonusTimers) {
            gameBonusTimer.timeForDestroying.stop();
        }
        this.gameBonusTimers.clear();

        this.gameBonusController.reset(this.view);

        this.resetPlayerAttributes();

        this.addBricks();
        this.addStartBall();

        this.gameController.setGameStatusEnum(GameStatusEnum.READY_TO_START_GAME);
        this.timer.start();
//        this.runTimers();
    }

    private void addStartBall() {
//        int sign = 1;
//        if (new Random().nextBoolean()) {
//            sign = -1;
//        }
//        this.gameController.addBall(new Ball(GameConstants.BALL_RADIUS, this.view.getWidth() / 2, this.view.getHeight() / 2, GameConstants.BALL_DIR_X * sign, GameConstants.BALL_DIR_Y));
        this.gameController.addBall(
                Ball.builder()
                        .setRadius(GameConstants.BALL_RADIUS)
                        .setPosX(this.view.getWidth() / 2 - GameConstants.BALL_RADIUS)
                        .setPosY(this.gameController.getPlayer().getPosY() - GameConstants.BALL_RADIUS * 2)
                        .setDirX(0)
                        .setDirY(GameConstants.BALL_DIR_Y)
                .build());
    }

    private void runTimers() {
        this.timer.start();
        this.addingBonusTimer.start();

        this.gamePause = false;
//        for (GameBonusTimer gameBonusTimer : this.gameBonusTimers) {
//            gameBonusTimer.timeForDestroying.start();
//        }
    }

    private void stopTimers() {
        this.timer.stop();
        this.addingBonusTimer.stop();

        gamePause = true;
//        for (GameBonusTimer gameBonusTimer : this.gameBonusTimers) {
//            gameBonusTimer.timeForDestroying.stop();
//        }
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

    public AdditionalMenuPositionEnum getAdditionalMenuPositionEnum() {
        return this.additionalMenuPositionEnum;
    }

    public class GameBonusTimer {
        private GameBonus gameBonus;
        private Timer timeForDestroying;
        private int counter;

        public GameBonusTimer(GameBonus gameBonus) {
            this.gameBonus = gameBonus;
            this.counter = 10;
            this.timeForDestroying = new Timer(Math.max(GameConstants.TIMER_BONUS_DESTROY / 10, 1), new EndOfBonusCycle(this));
            this.timeForDestroying.restart();
        }

        public boolean bonusIsActive() {
            return this.gameBonus.isUsed();
        }

        public GameBonus getGameBonus() {
            return this.gameBonus;
        }

        public void restartTimer() {
            this.counter = 10;
            this.timeForDestroying.restart();
        }
    }

    private class EndOfBonusCycle implements ActionListener {
        private GameBonusTimer gameBonusTimer;

        public EndOfBonusCycle(GameBonusTimer gameBonusTimer) {
            this.gameBonusTimer = gameBonusTimer;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (gamePause) {
                return;
            }
            --this.gameBonusTimer.counter;
            if (this.gameBonusTimer.counter > 0) {
                return;
            }
            if (!this.gameBonusTimer.gameBonus.isUsed()) {
                gameController.destroyGameBonus(this.gameBonusTimer.gameBonus);
            } else {
                gameBonusController.makeActive(this.gameBonusTimer.gameBonus, playerController.getPlayer(), gameController.getBalls(), gameController.getBricks(), view, false);
            }
            this.gameBonusTimer.timeForDestroying.stop();
            gameBonusTimers.remove(this.gameBonusTimer);
        }
    }

    public GameStatusEnum getGameStatusEnum() {
        return this.gameController.getGameStatusEnum();
    }


    public MenuPositionEnum getMenuPositionEnum() {
        return menuPositionEnum;
    }

    private boolean isBetween(int a, int x1, int x2) {
        return a >= x1 && a <= x2;
    }

    private int distBetween(int x1, int y1, int x2, int y2) {
        return (int)(Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)));
    }
 }
