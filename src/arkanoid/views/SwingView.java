package arkanoid.views;

import arkanoid.controllers.MainController;
import arkanoid.models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class SwingView extends JPanel implements View, KeyListener {

    private MainController mainController;
    private int width;
    private int height;

    private JFrame jFrame;

    private Vector<JLabel> menuLabels;
    private Vector<String> menuTexts;

    private JLabel scores;
    private JLabel lives;

    private Vector<Color> colors;

    public SwingView(int width, int height, MainController mainController) {
        super();

        this.width = width;
        this.height = height;
        this.mainController = mainController;

        this.createMenuTexts();
        this.createMenuLabels();
        this.createGameLabels();

        this.setUpJFrame();
        this.setUpPanel();
        this.jFrame.add(this);

        this.colors = new Vector<Color>();
        this.addMainColors();

    }

    private void createMenuLabels() {
        this.menuLabels = new Vector<JLabel>();
        for (int i = 0; i < this.menuTexts.size(); ++i) {
            this.menuLabels.add(new JLabel(this.menuTexts.get(i)));
            this.menuLabels.lastElement().setFont(new Font("TimesRoman", Font.BOLD, 55));
            this.menuLabels.lastElement().setForeground(Color.white);
        }
    }

    private void createMenuTexts() {
        this.menuTexts = new Vector<String>();
        this.menuTexts.add("CONTINUE");
        this.menuTexts.add("NEW GAME");
        this.menuTexts.add("EXIT");
    }

    private void createGameLabels() {
        this.scores = new JLabel("Scores: " + this.mainController.getScores());
        this.scores.setFont(new Font("TimesRoman", Font.BOLD, 25));
        this.scores.setForeground(Color.white);

        this.lives = new JLabel("Lives: " + this.mainController.getLives());
        this.lives.setFont(new Font("TimesRoman", Font.BOLD, 25));
        this.lives.setForeground(Color.white);
    }



    private void setUpPanel() {
        this.setBackground(Color.BLACK);

        this.setLayout(null);

        for (JLabel jLabel : this.menuLabels) {
            this.add(jLabel);
        }

        this.add(this.scores);
        this.add(this.lives);

        this.setFocusable(true);
        this.addKeyListener(this);
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
    public void keyTyped(KeyEvent keyEvent) {
        this.mainController.keyTyped(keyEvent);
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        this.mainController.keyPressed(keyEvent);
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        this.mainController.keyReleased(keyEvent);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        this.drawScene(graphics);
        if (this.mainController.getGameStatusEnum() != GameStatusEnum.GAME_IS_ON) {
            this.fillBackground(graphics);
            this.hideGameLabels();
            this.drawMenu(graphics);
        }
//
        Toolkit.getDefaultToolkit().sync();
        System.out.println("asd");
    }

    private void hideGameLabels() {
        this.remove(this.scores);
        this.remove(this.lives);
    }

    private void drawScene(Graphics graphics) {
        hideMenuLabels();
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
        this.lives.setBounds(5, 5 + this.scores.getPreferredSize().height, this.lives.getPreferredSize().width, this.lives.getPreferredSize().height);

        this.add(this.scores);
        this.add(this.lives);
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
            menuLabels.get(i).setBounds(this.width / 2 - menuLabels.get(i).getPreferredSize().width / 2 , this.height / 2 - menuLabels.get(i).getPreferredSize().height / 2 + (menuLabels.get(i).getPreferredSize().height * (i - menuTexts.size() / 2)), menuLabels.get(i).getPreferredSize().width, menuLabels.get(i).getPreferredSize().height);
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
