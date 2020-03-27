package arkanoid.views;

import arkanoid.controllers.MainController;
import arkanoid.models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.util.Vector;

public class SwingView extends JPanel implements View {

    private MainController mainController;
    private int width;
    private int height;

    private JFrame jFrame;

    private Vector<JLabel> menuLabels;

    private JLabel scores;
    private JLabel lives;
    private JLabel level;
    private JLabel gameOver;
    private JLabel gameOverScores;
    private JLabel levelComplete;

    private Vector<Color> colors;

    private int prevMenuPos = -1;

    public SwingView(int width, int height, MainController mainController) {
        super();

        this.width = width;
        this.height = height;
        this.mainController = mainController;

        this.createMenuLabels();
        this.createGameLabels();

        this.prepareGameOverScreen();
        this.prepareLevelCompleteScreen();

        this.setUpJFrame();
        this.setUpPanel();
        this.jFrame.add(this);

        this.colors = new Vector<Color>();
        this.addMainColors();

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
                mainController.keyTyped(keyEvent);
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                mainController.keyPressed(keyEvent);
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                mainController.keyReleased(keyEvent);

            }
        });

    }

    private void prepareLevelCompleteScreen() {
        levelComplete = new JLabel("LEVEL COMPLETE");
        levelComplete.setFont(new Font("TimesRoman", Font.BOLD, 65));
        levelComplete.setForeground(Color.WHITE);
    }

    private void prepareGameOverScreen() {
        gameOver = new JLabel("GAME OVER");
        gameOver.setFont(new Font("TimesRoman", Font.BOLD, 65));
        gameOver.setForeground(Color.WHITE);
        gameOverScores = new JLabel("Scores");
        gameOverScores.setFont(new Font("TimesRoman", Font.BOLD, 65));
        gameOverScores.setForeground(Color.WHITE);
    }

    private void createMenuLabels() {
        Vector<String> menuTexts = this.mainController.getMenuTexts();
        this.menuLabels = new Vector<JLabel>();
        for (int i = 0; i < menuTexts.size(); ++i) {
            this.menuLabels.add(new JLabel(menuTexts.get(i)));
            this.menuLabels.lastElement().setFont(new Font("TimesRoman", Font.BOLD, 55));
            this.menuLabels.lastElement().setForeground(Color.LIGHT_GRAY);
        }
    }

    private void createGameLabels() {
        this.scores = new JLabel("Scores: " + this.mainController.getScores());
        this.scores.setFont(new Font("TimesRoman", Font.BOLD, 25));
        this.scores.setForeground(Color.white);

        this.lives = new JLabel("Lives: " + this.mainController.getLives());
        this.lives.setFont(new Font("TimesRoman", Font.BOLD, 25));
        this.lives.setForeground(Color.white);

        this.level = new JLabel("Level: " + this.mainController.getLevel());
        this.level.setFont(new Font("TimesRoman", Font.BOLD, 25));
        this.level.setForeground(Color.white);
    }



    private void setUpPanel() {
        this.setBackground(Color.BLACK);

        this.setLayout(null);

        for (JLabel jLabel : this.menuLabels) {
            this.add(jLabel);
        }

        this.add(this.scores);
        this.add(this.lives);
        this.add(this.level);

        this.setFocusable(true);
    }

    private void setUpJFrame() {
        this.jFrame = new JFrame("Arkanoid");
        this.jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jFrame.setSize(this.width, this.height);
        this.jFrame.setResizable(false);
        this.jFrame.setVisible(true);
    }

    private void addMainColors() {
        this.colors.add(Color.red);
        this.colors.add(Color.orange);
        this.colors.add(Color.yellow);
        this.colors.add(Color.green);
        this.colors.add(Color.cyan);
        this.colors.add(Color.blue);
        this.colors.add(Color.pink);
    }

    @Override
    public void setController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void updateView() {
//        paintComponent(this.getGraphics());
        this.repaint();
//        this.paint(this.getGraphics());
//        this.update(this.getGraphics());
//        this.print(this.getGraphics());
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public void closeView() {
        this.jFrame.dispatchEvent(new WindowEvent(this.jFrame, WindowEvent.WINDOW_CLOSING));
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        this.drawScene(graphics);
        if (this.mainController.getGameStatusEnum() == GameStatusEnum.GAME_IS_PAUSE || this.mainController.getGameStatusEnum() == GameStatusEnum.GAME_IS_START) {
            this.fillBackground(graphics);
            this.hideGameLabels();
            this.drawMenu(graphics);
        } else if (this.mainController.getGameStatusEnum() == GameStatusEnum.GAME_IS_OVER) {
            this.drawGameOverScreen(graphics);
        } else if (this.mainController.getGameStatusEnum() == GameStatusEnum.LEVEL_COMPLETE) {
            this.drawLevelCompleteScreen(graphics);
        }
//
        Toolkit.getDefaultToolkit().sync();
    }

    private void drawLevelCompleteScreen(Graphics graphics) {
        levelComplete.setText("LEVEL " + this.mainController.getLevel() + " COMPLETE");
        levelComplete.setBounds(this.width / 2 - levelComplete.getPreferredSize().width / 2 , this.height / 2 - levelComplete.getPreferredSize().height * 3 / 2, levelComplete.getPreferredSize().width, levelComplete.getPreferredSize().height);
        this.add(levelComplete);
    }

    private void drawGameOverScreen(Graphics graphics) {
        gameOver.setBounds(this.width / 2 - gameOver.getPreferredSize().width / 2 , this.height / 2 - gameOver.getPreferredSize().height * 3 / 2, gameOver.getPreferredSize().width, gameOver.getPreferredSize().height);
        this.add(gameOver);

        gameOverScores.setText("Scores: " + this.mainController.getScores());
        gameOverScores.setBounds(this.width / 2 - gameOverScores.getPreferredSize().width / 2 , this.height / 2 - gameOverScores.getPreferredSize().height / 2, gameOverScores.getPreferredSize().width, gameOverScores.getPreferredSize().height);
        this.add(gameOverScores);
    }

    private void hideGameLabels() {
        this.remove(this.scores);
        this.remove(this.lives);
        this.remove(this.level);
    }

    private void drawScene(Graphics graphics) {
        hideMenuLabels();
        this.remove(this.gameOver);
        this.remove(this.gameOverScores);
        this.remove(this.levelComplete);
//
        updateGameLabels();
        drawGameBonuses(graphics);
        drawBall(graphics);
        drawBricks(graphics);
        drawPlayer(graphics);
        drawActiveGameBonuses(graphics);
    }

    private void updateGameLabels() {
        this.scores.setText("Scores: " + this.mainController.getScores());
        this.scores.setBounds(5, 5, this.scores.getPreferredSize().width, this.scores.getPreferredSize().height);
//
        this.lives.setText("Lives: " + this.mainController.getLives());
        this.lives.setBounds(5, 5 + this.lives.getPreferredSize().height, this.lives.getPreferredSize().width, this.lives.getPreferredSize().height);

        this.level.setText("Level: " + this.mainController.getLevel());
        this.level.setBounds(5, 5 + this.scores.getPreferredSize().height + this.lives.getPreferredSize().height, this.level.getPreferredSize().width, this.level.getPreferredSize().height);

        this.add(this.scores);
        this.add(this.lives);
        this.add(this.level);
    }

    private void hideMenuLabels() {
        for (JLabel jLabel : this.menuLabels) {
            this.remove(jLabel);
        }
    }

    private void showMenuLabels() {
        for (JLabel jLabel : this.menuLabels) {
            this.add(jLabel);
        }
    }

    private void drawMenu(Graphics graphics) {
        this.showMenuLabels();
        for (int i = 0; i < this.menuLabels.size(); ++i) {
            menuLabels.get(i).setBounds(this.width / 2 - menuLabels.get(i).getPreferredSize().width / 2 , this.height / 2 - menuLabels.get(i).getPreferredSize().height / 2 + (menuLabels.get(i).getPreferredSize().height * (i - this.menuLabels.size() / 2)), menuLabels.get(i).getPreferredSize().width, menuLabels.get(i).getPreferredSize().height);
//            menuLabels.get(i).setForeground(Color.LIGHT_GRAY);
        }
        int menuPos = this.mainController.getMenuPos();
        if (menuPos >= 0 && menuPos < this.menuLabels.size()) {
            if (this.prevMenuPos != menuPos) {
                if (prevMenuPos != -1) {
                    this.menuLabels.get(this.prevMenuPos).setForeground(Color.LIGHT_GRAY);
                }
                this.menuLabels.get(menuPos).setForeground(Color.WHITE);
                this.prevMenuPos = menuPos;
            }

        }
    }

    private void drawActiveGameBonuses(Graphics graphics) {
        Vector<MainController.GameBonusTimer> gameBonusTimers = this.mainController.getGameBonusTimers();
        for (int i = 0; i < gameBonusTimers.size(); ++i) {
            if (gameBonusTimers.get(i).bonusIsActive()) {
                graphics.setColor(this.colors.get(gameBonusTimers.get(i).getGameBonus().getBonusEnum().getIntRepr() % this.colors.size()));
                graphics.fillRect(50 + 40 * i, this.height - 75, 20, 20 );
            }
        }
    }

    private void drawGameBonuses(Graphics graphics) {
        Vector<GameBonus> gameBonuses = this.mainController.getGameBonuses();
        for (GameBonus gameBonus : gameBonuses) {
            graphics.setColor(this.colors.get(gameBonus.getBonusEnum().getIntRepr() % this.colors.size()));
            graphics.fillRoundRect(gameBonus.getPosX(), gameBonus.getPosY(), gameBonus.getWidth(), gameBonus.getHeight(), gameBonus.getWidth() / 2, gameBonus.getHeight() / 2);
        }
    }


    private void fillBackground(Graphics graphics) {
        graphics.setColor(new Color(0, 0, 0, 200));
        graphics.fillRect(0, 0, this.width, this.height);
    }

    private void drawBall(Graphics graphics) {
        graphics.setColor(Color.yellow);
        Vector<Ball> balls = this.mainController.getBalls();
        for (Ball ball : balls) {
            graphics.fillOval(ball.getPosX(), ball.getPosY(), ball.getDiameter(), ball.getDiameter());
        }
    }

    private void drawBricks(Graphics graphics) {
        Vector<Brick> bricks = this.mainController.getBricks();
        for (Brick brick : bricks) {
            if (brick.getHitsForDestroying() > 2) {
                graphics.setColor(Color.LIGHT_GRAY);
            } else if (brick.getHitsForDestroying() > 1) {
                graphics.setColor(Color.GRAY);
            } else {
                graphics.setColor(Color.DARK_GRAY);
            }
            graphics.fillRect(brick.getPosX(), brick.getPosY(), brick.getWidth(), brick.getHeight());
        }

    }

    private void drawPlayer(Graphics graphics) {
        graphics.setColor(Color.WHITE);
        Player player = this.mainController.getPlayer();
        graphics.fillRoundRect(player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight(), player.getHeight(), player.getHeight());
    }

}
